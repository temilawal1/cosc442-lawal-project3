Initial Coverage

VendingMachine.java: 43/43 statement/line, 20/20 branch
VendingMachineItem.java: 8/8 statement/line, 2/2 branch

Final Coverage

VendingMachine.java: 43/43 statement/line, 20/20 branch
VendingMachineItem.java: 8/8 statement/line, 2/2 branch

Condition-Coverage Analysis

In makePurchase(), there are two boolean conditions that make up the decision if ((item != null) && (this.balance >= item.getPrice())). Item != null and this.balance >= item.getPrice(). testMakePurchase() evaluates the decision to true and true, testMakePurchase_NotEnoughFunds() evaluates the decision to true and false, and testMakePurchase_EmptySlot evaluates the first condition to false. Each condition is evaluated to true and false at least once.

Test-Sensitivity Experiment

Fault introduced: in insertMoney() method, changed this.balance += amount to this.balance -= amount

Failed tests: testReturnChange(), testMakePurchase_ExactFunds, testMakePurchase(), and testInsertMoney

