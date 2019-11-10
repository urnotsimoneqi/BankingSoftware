# BankingSoftware
The final project for IS5009. A mobile application which is intended to motivate the customers to save money in order to reach their goals.

username: user1@demo.com ~ user6@demo.com

password: 123456

## Goal Tab
### Done
- Implement listing goals
- Connect to sqlite database
- Implement showing goal detail
- Implement UI of creating Goal
- Implement UI of saving money
- Implement sharing to social platform
- Hide the create button when user already has a group goal in progress
- Add animation when put money to the goal 
- Log in/Log out
- Show prompt dialog when achieved the goal

### To Do
- Inviting friends
- Update the UI of saving money and goal detail
- Revise some code logic of creating goal

## Group Tab
### Done
- List group
- Join group
### To Do
- Search group
- Revise some code logic of joining group
## Bug to Fix

 
## Bug Fixed
- Need to tap the last tab first otherwise the view of first tab not show
- Goal Tab: The view of Goal fragment does not refresh but the data updated(join a group)
- Fragment load before loginActivity so the userInfo may be null
- Problem of displaying the progress (float, int, String)
- groupId in table "goal" and "user_group" is null when create goal and group
- Modify the SQL when update multiple field in database
## Note
- User can only join other's group when he does not create a group
