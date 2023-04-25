# External Sort

An implementation of an external sorting algorithm in Java. Takes large standard input and distributes into runs from a limited memory heap. Runs are then merged using a balanced k-way merge sort. The sorted file is sent to standard output.

Usage: `cat <input> | java CreateRuns <run size> | java MergeRuns [k] > <output>`
