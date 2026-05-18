# MongoDB for Shein

    mongosh mongodb://luke:luke@52.3.85.231:27017/?authSource=lukedb

## Category/Conditions Mapping

MongoDB is **schemaless** but each collection should still have a **schema**. Developers and DBAs should agree on a collection
before putting into practice. Clearly, this is a general guideline and not a hard-and-fast rule.

    # bad - totally schemaless
    #
    (bad) db.CategoryConditions.insertMany([
      { electronics: ['Brand New - Sealed', 'New - Open box', 'New - Seller refurbished', 'Like New - Excellent', 'Seller refurbished', 'Very Good', 'Good', 'Acceptable'] },
      { tool: ['New', 'New - Open box', 'Seller refurbished', 'Used', 'For parts or not working'] },
      { cloth: ['New with tags', 'New without tags', 'New with defects', 'Pre-owned'] },
      { other: ['Brand New - Sealed', 'New - Open box', 'Like New - Excellent', 'Very Good', 'Good', 'Acceptable'] },
    ])

    # good - with schema
    #
    db.CategoryConditions.insertMany([
      { category: 'electronics',
        conditions: ['Brand New - Sealed', 'New - Open box', 'New - Seller refurbished', 'Like New - Excellent', 'Seller refurbished', 'Very Good', 'Good', 'Acceptable'] },
      { category: 'tool',
        conditions: ['New', 'New - Open box', 'Seller refurbished', 'Used', 'For parts or not working'] },
      { category: 'cloth',
        conditions: ['New with tags', 'New without tags', 'New with defects', 'Pre-owned'] },
      { category: 'other',
        conditions: ['Brand New - Sealed', 'New - Open box', 'Like New - Excellent', 'Very Good', 'Good', 'Acceptable'] },
    ])

    # rename a collection
    #
    # db.CategoryCondition.renameCollection('CategoryConditions')
