test plan

valid beavior - does the method produce the expected result when used normally with valid inputs and appropriate preconditions?
exception/invalid behavior - what should happen when invalid input is supplied or a required precondition is violated? if an exception si specified, does the correct exception occur?
boundary behavior - at what values or conditions does the program's behavior change? Test values immediately below, at, and above an important boundary when appropriate



method/behavior

1. add item test
-> the method does produce the expected result when used normally with valid inputs and appropriate preconditions
2. get item
3. remove item
4. insert money
5. get balance
6. make purchase
7. return change

valid cases
1. add item test


exception/invalid cases
1. add item test
-> enter an int instead of a string for the code, add something to an already occupied slot, add an item to another slot

boundary cases


oracle/expected result


related junit tests