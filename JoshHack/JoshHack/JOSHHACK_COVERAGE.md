Initially:
23/24 statements covered, 6/7 functions covered, 16/18 branches covered
not covered: if items[i] != null, return size == items.length, getItems();

changes:
24/24 statements covered, 7/7 functions covered, 16/18 branches covered: added testGetItems. Just adds an item and tests the GetItems() method. Somewhat unnecessary because the get() method can be used to do the same thing, but i saw it wasn't covered and wanted to cover it.

2 branches are uncovered because I think adding a test that executes the false outcome of items[i] 1= null is not necessary because we know that the Boolean executes properly already. I think it would be helpful because then you could see if the add item method is working properly, but that is displayed in many other tests already.
