select p.firstName, p.lastName, a.city, a.state
from person as p
left outer join address as a
on p.personId = a.personId;