# oiiku-data-validation

oiiku-data-validation is a Clojure library to validate data.

## Basic usage

    (require [oiiku-mongodb.validator :as v])

    (def validator
      (v/validator
        (v/validate-presence :username)
        (v/validate-presence :password)
        (fn [data]
          {:base ["An arbitrary function returning error message for the entire record"]}
        (fn [data]
          {:attr {:some-attr ["An arbitrary function returning an error message for a specific attribute]}})))

## A note on strings vs keywords in maps

This library only supports maps where the keys are keywords.

In some cases where you have multiple data sources, like JSON parsing and a document database, you'll have maps with strings as keys and others with keywords as keys. This is a messy situation, and this library does not try to do anything to mitigate it. In all cases it does `(attr some-map)` for lookup, which will only work for keywords anyways.

Cheshire can parse to keywords, `(cheshire.core/parse-string json-string true`).

In some clojure version, IIRC 1.2, keywords are garbage collected, so it's safe to turn user input into keywords.

## Built-in validators

In all cases you can replace the custom error message function with a string and that will be used as the error, incases where you don't need validation execution pecific data to be present in the error message.

### `(v/validate-presence :some-attr (fn [] "custom err msg"))`

Passes if the data map contains the specified key.

### `(v/validate-non-empty-string :some-attr (fn [] "is nil msg") (fn [] "is not string msg"))`

Passes if the data is non-nil and a string longer than zero.

### `(v/validate-only-accept [:some-attr :some-other-attr] (fn [extraneous-attrs] "custom err msg"))`

Passes if the map only contains the specified list of keys. In the example above, `{:some-attr "foo"}` and `{:some-attr "foo" :some-other-attr "bar"}` is valid. `{:blargh-attr "foo"}` and `{:some-attr "foo" :cake "bar"}` is invalid.

The error message function is passed a set of the attributes that was present and not accepted.

### `(v/validate-inclusion :some-attr ["list of", "accepted values"] (fn [accepted-values] "err msg"))`

Passes if the specified attr is exactly the value of one of the items in the list.

## Chaining validators

You can chain validators so that the second only executes if the first one returns no error, and so on.

    (v/chain
      (v/validate-presence :username)
      (fn [data]
        ;; Do something that requires (:username data) to be non-nil
        ;; Won't run if validate-presence returns an error
        ))

Note that you are completely free to implement your own chaining if you so wish.

## Nested data

You can create validators that only operates on a subset of the data.

    (v/validator
      (v/validate-presence :username)
      (v/validate-record
        :some-field
        (v/validator
          (v/validate-presence :nested-stuff))))

This is useful for validating structures like this:

    {:username "Foo", :some-field {:nested-stuff "stuff here"}}

The validator `some-validator` can be any validator, such as `(validate-presence :foo)` or `(v/validate ....)`.

The data passed to the validators will be (:some-field full-data).

The errors returned by the validator will be added in full to the attribute in question.

    {:attr {:some-field {:base ["Errors on base"], :attr {:some-attr ["some err"]}}}}


## Lists

You can create validators that operates on lists of data.

    (v/validator
      (v/validate-presence :username)
      (v/validate-record-list
        :thingies
        (v/validator
          (v/validate-presence :foo))))

This is useful for validating structures like this:

    {:username "Foo", :thingies [{:foo "123"}, {:foo "456"}]}

The errors returned looks like this, where the key in the map is the index of the item in the provided list.

    {:attr {:thingies {2 {:attr {:foo ["is required"]}}}}}

An error will be returned when the data is _not_ a list. The error messages you get here is currently not configurable, which sucks, file new issues like mad until we fix that.

## Impromptu validators based on incoming data

TODO: Implement this. Useful in document databases where documents needs to be validated based on some schema specification in another document, or whatever

## Passing extra data to validations

TODO: Learn monads (I think) and implement this

## Creating your own validator functions

A validator function is a function that takes the provided data and returns null, or a map of errors.

The map of errors must look like the following:

    {:base ["A list of", "Error messages"]}
    {:attrs {:username ["Already exists"]}}
    {:attrs {:username ["Already exists"]} :base ["An unknown error occurred (db exception)"]}

You are free to screw this up, but the library won't do any validation to help you out. So make sure your validators returns a correct map to avoid wacko errors.

## Separation of top-level errors and attribute specific errors

If you're creating a new user and want to return the error "quota exceeded", which attribute should you put that error message on?

The answer is no attribute. This is solved by oiiku-data-validation since it separates top-level base errors and attribute specific errors.

    {:base ["quota exceeded"], :attr {:email ["must contain an @"]}}

By completely separating :base and :attr you are free to have an attribute called `:base` without fear of conflicts.
