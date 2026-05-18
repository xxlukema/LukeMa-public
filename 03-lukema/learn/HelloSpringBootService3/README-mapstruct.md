# `mapstruct`

[Tutorial]<https://www.tutorialspoint.com/mapstruct/mapstruct_implicit_type_conversion.htm>

## MapStruct - Implicit Type Conversion

MapStruct handles conversion of type conversions automatically in most of the cases. For example, int to Long or String conversion.
Conversion handles null values as well. Following are the some of the important automatic conversions.

- Between primitive types and Corresponding Wrapper Classes.
- Between primitive types and String.
- Between enum types and String.
- Between BigInt, BigDecimal and String.
- Between Calendar/Date and XMLGregorianCalendar.
- Between XMLGregorianCalendar and String.
- Between Jodas date types and String.
