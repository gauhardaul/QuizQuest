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
