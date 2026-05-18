# Design

## Inheritance vs Containment

Classes and objects created through inheritance are tightly coupled because changing the parent or superclass in an inheritance relationship
risks breaking your code. Classes and objects created through composition are loosely coupled, meaning that you can more easily change the
component parts without breaking your code.

## MongoDB Schema Design

[MongoDB Schema Design Best Practices]<https://www.youtube.com/watch?v=leNCfU5SYR8>

16 MB size limit

One to One
One to A Few
One to Many
One to Squillion (Example: log recodes with reverse reference to hostname)
Many to One
Many to Many
Two Way Reference

### Embedded vs Reference (`ObjectId`)

    {_id: ObjectId("aabbcd")}

- Use referencing to avoid update containing records everywhere. Example: Need to update contained object in one place.
- Favor embedded unless there is a compelling reason not to.
- Avoid JOINs and $lookups if they can be avoided.
- Arrays should not grow without bound. Use reversing referencing (Many to One) in case of unbounded referencing.
- Model your data depends **entirely** on app data access pattern.

## Kafka Topic
