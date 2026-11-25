# QuizQuest

QuizQuest is an Android quiz app built with Kotlin and Jetpack Compose. It includes
Math, IT, and Science quizzes and a history of completed attempts.

## Online question source

The Week 13 network layer requests three multiple-choice questions per category
from [Open Trivia Database](https://opentdb.com/). The API requires an internet
connection and does not require an API key. Open Trivia Database asks clients to
wait at least five seconds between requests from the same IP address.

Question content returned by Open Trivia Database is licensed under
[CC BY-SA 4.0](https://creativecommons.org/licenses/by-sa/4.0/).
QuizQuest decodes question text and shuffles the answer order before display.

## Theme setting

Use **Dark theme** on the topic selection screen to switch between the QuizQuest
light and dark color schemes. The app follows the device theme until you make a
selection. After that, Preferences DataStore keeps your choice for the next launch.

To check it on a device, select dark mode, close the app completely, and reopen it.
Repeat with light mode. The **Dark theme** switch should reflect the saved choice,
and the quiz, results, and history should use the same colors as the topics screen.
