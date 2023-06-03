# Semantic Analyzer

This is a semantic analyzer for Mini Java parts. It validates the syntax and semantics of the provided code, ensuring that it follows the defined rules for data types, variables, operators, and expressions.

## Accepting Types

The semantic analyzer accepts the following types:

- Datatype: `int`, `double`, `String`, `char`
- Op: `+`, `*`, `/`, `-`, `%`
- Var: `!Keyword && ^[_a-zA-Z][0-9a-zA-Z]*`
- Keyword: `"abstract"`, `"continue"`, `"for"`, `"new"`, `"switch"`, `"assert"`, `"default"`, `"goto"`, `"package"`, `"synchronized"`, `"true"`, `"false"`, `"null"`, `"boolean"`, `"do"`, `"if"`, `"private"`, `"this"`, `"break"`, `"double"`, `"implements"`, `"protected"`, `"throw"`, `"byte"`, `"else"`, `"import"`, `"public"`, `"throws"`, `"case"`, `"enum"`, `"instanceof"`, `"return"`, `"transient"`, `"catch"`, `"extends"`, `"int"`, `"short"`, `"try"`, `"char"`, `"final"`, `"interface"`, `"static"`, `"void"`, `"class"`, `"finally"`, `"long"`, `"strictfp"`, `"volatile"`, `"const"`, `"float"`, `"native"`, `"super"`, `"while"`, `"String"`, `"float"`, `"long"`
- Val: `Int`, `Char`, `String`, `Double`, `not initialized`, `Invalid`

## Grammar

The following grammar rules define the structure of the code that can be analyzed by this semantic analyzer:

```
Code    : start STAT stop

STAT    : DECL | ASSIGN

DECL    : Datatype Var Eq ASSIGNVAL; | Datatype Var ;

ASSIGN  : Var Eq Val | Var Eq Var | Var Eq EXPRSN

ASSIGNVAL : Var | Val

EXPRSN  : Val Op Val | Var Op Val | Val Op Var | Var Op Var
```

## Valid Assignments

The semantic analyzer accepts the following valid assignments:

- `int`:
  - `int`: `int`
  - `char`: `char`
  - `char,int`: `char`, `int`
  - `double`: `int`, `char`, `double`
  - `String`: `String`

## Expression Validation

The semantic analyzer validates expressions based on the specified operators (`Op`). The following rules define the validity of expressions for different operators:

- For `Op = "+"`:
  - `int`, `char`, `String`, `double` can be combined in any valid order:
    - `int + char`
    - `char + double`
    - `String + double`
    - `String + char`
    - `double + double`
    - `double + String`
    - `double + double + double`
    - `String + String + String`
- For other operators (`*`, `/`, `-`, `%`):
  - `String` is invalid for operators other than `+`
  - For `Op = "-"`, if the value is less than 0 (`< 0`), a `char` throws an error:
    - `int - char`
    - `double - char`
    - `double - double - double`
    - `double - double + double`
    - `double - double * double`
    - `double - double / double`

Please note that the expressions provided above are just examples

 and do not cover all possible combinations. The analyzer will evaluate the expressions based on the defined rules.

Feel free to modify and enhance this semantic analyzer according to your specific requirements!
