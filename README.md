# Jackson Java Iterator error when using with Flink

The codebase represents the same JSON being parsed with custom method
to similar model `case classes`, which contain a collection using 
`Iterator`, `Seq` or `List`.

They seem to have the same root cause, but I do not understand the reason.
**What is more, currently only eager initialization seems to fix the issue.**

This is the status of the problem:

|             | `Iterator`         | `Seq`              | `List`                   |
|-------------|------------        |-------             |--------                  |
| *2.11.12*   | :heavy_check_mark: | :x:                | :heavy_check_mark:       |
| *2.12.13*   | :x:                | :x:                | :heavy_check_mark:       |

For some reason with *Scala 2.12* there is even more problems, but at least the stacktrace 
seems to be more informative. Not that I know what is wrong with the model or parsing method,
but the problems seems to lay in `Kryo` not in the `Iterator` itself.

## Scala 2.11
### Seq

```
Caused by: java.util.ConcurrentModificationException
	at java.util.ArrayList$Itr.checkForComodification(ArrayList.java:911)
	at java.util.ArrayList$Itr.next(ArrayList.java:861)
	at scala.collection.convert.Wrappers$JIteratorWrapper.next(Wrappers.scala:43)
	at scala.collection.Iterator$$anon$11.next(Iterator.scala:410)
	at scala.collection.Iterator$class.toStream(Iterator.scala:1320)
	at scala.collection.AbstractIterator.toStream(Iterator.scala:1334)
	at scala.collection.Iterator$$anonfun$toStream$1.apply(Iterator.scala:1320)
	at scala.collection.Iterator$$anonfun$toStream$1.apply(Iterator.scala:1320)
	at scala.collection.immutable.Stream$Cons.tail(Stream.scala:1233)
	at scala.collection.immutable.Stream$Cons.tail(Stream.scala:1223)
	at scala.collection.immutable.Stream$$anonfun$append$1.apply(Stream.scala:255)
	at scala.collection.immutable.Stream$$anonfun$append$1.apply(Stream.scala:255)
	at scala.collection.immutable.Stream$Cons.tail(Stream.scala:1233)
	at scala.collection.immutable.Stream$Cons.tail(Stream.scala:1223)
	at scala.collection.generic.Growable$class.loop$1(Growable.scala:54)
	at scala.collection.generic.Growable$class.$plus$plus$eq(Growable.scala:57)
	at scala.collection.mutable.ListBuffer.$plus$plus$eq(ListBuffer.scala:183)
	at scala.collection.mutable.ListBuffer.$plus$plus$eq(ListBuffer.scala:45)
	at scala.collection.TraversableLike$class.to(TraversableLike.scala:590)
	at scala.collection.AbstractTraversable.to(Traversable.scala:104)
	at scala.collection.TraversableOnce$class.toList(TraversableOnce.scala:294)
	at scala.collection.AbstractTraversable.toList(Traversable.scala:104)
	at com.github.atais.SeqModel$.flatten(SeqModel.scala:32)
```

[Full stacktrace](scala211seq.stacktrace)

## Scala 2.12

### Seq

```
Caused by: java.lang.NullPointerException
	at com.esotericsoftware.kryo.util.DefaultClassResolver.writeClass(DefaultClassResolver.java:80)
	at com.esotericsoftware.kryo.Kryo.writeClass(Kryo.java:488)
	at com.esotericsoftware.kryo.serializers.ObjectField.write(ObjectField.java:57)
	... 29 more
```

[Full stacktrace](scala212seq.stacktrace)

### Iterator

```
Caused by: java.lang.NullPointerException
	at com.esotericsoftware.kryo.util.DefaultClassResolver.writeClass(DefaultClassResolver.java:80)
	at com.esotericsoftware.kryo.Kryo.writeClass(Kryo.java:488)
	at com.esotericsoftware.kryo.serializers.ObjectField.write(ObjectField.java:57)
	... 23 more
```

[Full stacktrace](scala212iterator.stacktrace)
