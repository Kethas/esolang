# Esolang/Fox #

A small scripting language written in Java.

### Getting Started ###
The first thing you have to do is clone the repository and build it. This can be done using any Java IDE.
The resulting JAR file will execute the file specified by the first argument given to it when run.

### Basic Fox Syntax ###
There are only four types in Fox:
 * String 
 * Integer
 * Function
 * and Null

A variable of each type looks as follows:
```
var int = 123123;
var string = "hellohello";
var function = lambda x -> x;
var nullVar = null;
```

You can get the type of a variable using `typeof(x)`:

```
println(typeof(int)) # prints "int"
println(typeof(string)) # prints "string"
println(typeof(function)) # prints "function"
println(typeof(nullVar)) # prints "null"
```


In Fox, functions are only anonymous, and are declared using a variable:
```
var f = lambda x, y -> x + y # immediate return
var g = lambda x, y, z { # compound statement
    println(x + y + z)
    return f(y, x * z)
}

var recursive = lambda n -> # functions can even be recursive!
    if n < 0 -> null
    else -> recursive(n - 1)
```

See the 'examples' folder for more examples.