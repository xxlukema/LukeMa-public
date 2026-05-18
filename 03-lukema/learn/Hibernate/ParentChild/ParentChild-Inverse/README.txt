
Think inverse as the "ignore" 
inverse = true means to ignore the relationship of this side
inverse = false means not to ignore the relationship of this side



http://blogs.warwick.ac.uk/colinyates/entry/hibernates_bizzare_interpretation/

April 25, 2006
Hibernates bizarre interpretation of inverse ;)

Writing about web page hibernate.org

So my colleague asked me about the exact semantics of hibernate's "inverse" keyword which is surely the worst choice of word for a simple concept.

Essentially "inverse" indicates which end of a relationship should be ignored, so when persisting a parent who has a collection of children, should you ask the parent for its list of children, or ask the children who the parents are?

Why does this matter? Well if you are stupid then you might do something like:


  Parent parentA = new Parent();
  Parent parentB = new Parent();
  Child child = new Child();
  parentA.getChildren().add(child);
  child.setParent(parentB);

how should hibernate persist this situation? For unidirectional one每to每many it is trivial; only one end of the relationship is modelled (there is only parent.addChild(), not child.getParent()), but when it is bidirectional (parent.getChild and child.getChildren) you need to indicate whether the one每to每many is inverse or not.

What does it mean to be inverse? It informs hibernate to ignore that end of the relationship. If the one每to每many was marked as inverse, hibernate would create a child每>parentB relationship (child.getParent). If the one每to每many was marked as non每inverse then a child每>parentA relationship would be created.

Or at least that is what you would think :) First moan is why use the word "inverse"? By setting it to true you are effectively telling hibernate to ignore it. Setting something to true to exclude something is reverse logic and is incredibly unintuitive. Maybe "owner" would be a better term?

The second moan is that hibernate isn't consistent. If the parent每>child is not inverse (i.e. parent.getChildren will be used to define the relationship) then you would expect calling child.setParent to have no effect. This is not the case. Calling child.setParent does create the relationship. If you end up with the idiotic case I proposed earlier, than hibernate will give precendece to the parent.getChildren (as it should), but to be consistent child.setParent(parentA) should not create the relationship.

It is such a simple concept, but so many people seem to struggle with it. I really don't think hibernates terminology is very helpful.

Simply put; if you set inverse=true on a one每to每many then child.getParent will be called, if inverse=false then parent.getChildren will be called.

Of course, good developers will not run into this problem because they will all ensure that parents have appropriate accessors (parent.addChild, parent.removeChild etc.) :)

The invertedness (?) of the relationship does have quite an impact on performance and schema design; if the one每to每many is not inverse (default) then everytime you create a child, hibernate will execute 2 statements, one to create the child, one to update the child with the foreign key of the parent. Foreign key constraints/not null constraints? hah, who needs those anyway :) Hibernate will also instantiate the collection everytime you add a child＃.not sure why. Setting inverse to true will fix both of these annoyances/downright ridiculous design decisions.

One last note; cascade and inverse are completely orthogonal. Cascade simply tells hibernate how to reach objects. If I set inverse=true on a one每to每many then hibernate will not use parent.getChildren to define the relationship. If I set cascade to save, or all then hibernate will call parent.getChildren to see if there are any new children. Setting inverse=true and calling child.setParent will have absolutely no effect because hibernate won't know about your new child :)

So summary:

每 always provide parent.addChild which updates both ends of the relationship

每 always set inverse to true on bidirectional one每to每many

每 make sure you understand the difference between cascades and inverse ;)




April 26, 2006
Inverse attribute of Hibernate

Think inverse as the ※ignore§
inverse = true means to ignore the relationship of this side
inverse = false means not to ignore the relationship of this side

In parent-child relationship, parent is one side and children are many side.

one-to-many inverse=§false§
means relationship of parent side should not be ignored. So
parent.getChildren() will be examined, child.getParent() is ignored

For the following example:

Parent parentA = new Parent();
Parent parentB = new Parent();
Child  child = new Child();
parentA.getChildren().add(child); Will be used
child.setParent(parentB); Will be ignored


