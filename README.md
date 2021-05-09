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

### Fox Standard functions ###
I have added a few external helper functions to Fox in order to make it more useful.

* `println(x, y, ..., z)` - prints out (to `stdout`) its arguments separated by a single space and followed by a newline after the final argument.
* `readln(prompt?)` - prints out `prompt` (if there is one) and blocks until the user enters a line to `stdin`.
* `file_read(path)` - returns the contents (in the form of a string) of the file specified by the string `path`.
* `file_write(path, contents)` - writes (overwrites, if necessary) `contents` to the file specified by the string `path`.
* `file_append(path, contents)` - appends `contents` to the end of the file specified by the string `path`.
* `charAt(o, i)` - returns an integer representing the character of `o` turned into a string at the index specified by `i`.
* `toint(o)` - attempts to convert `o` into an integer.
* `typeof(o)` - returns the string representation of the data type of `o`.

Try not to break these functions (by passing illegal indices or paths) because it will throw a Java exception, 
and not necessarily just return null in Fox.


### Why ###
This project was a learning experience for me. I had wanted to make my own programming language for quite a while, and when 
I found the tutorial LSBASI (https://ruslanspivak.com/lsbasi-part1/) I immediately started to follow it. While the original 
Interpreter is written in Python, I decided to use Java for mine, as I was more familiar with Java.

I had also wanted to make this programming language more functional, yet still a scripting language. This was, unfortunately,
before I had learnt Haskell or anything else functional. As a result, functions are always first-class, though they receive
a list of arguments and cannot be called partially. Also, variables are always mutable and all side effects are allowed, 
something that, today, is considered highly un-functional.

This project, however un-functional it is, did teach me a lot about how programming languages are lexed and parsed, and 
how to write simple lexers and parsers.