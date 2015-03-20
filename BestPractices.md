# Introduction #

This page will document best-practices as they are come across.

# Atomic Actions #

To make best use of the 'rollback' function, it's generally a good idea that any given action will only make a single state change in a single execution. This makes it simpler to execute a rollback since it doesn't have to worry about situations where one state change succeeded and the other failed. See the CompoundActions example for details.

It is ok to have your handler execute multiple other actions via `ExecutionContext` _and_ execute a single state change, because any failure will automatically roll back other actions executed via `ExecutionContext`. However, ensure that you are only making at most one state change.

**Note:** You can usually safely consider anything within a transaction to be a 'single state change' since it should take care of rollback internally.