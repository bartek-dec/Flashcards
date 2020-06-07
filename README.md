# Flashcards

### This is a console project to play with flashcards in the following manner: program displays a hint and expects user's answer. After providing the answer, program displays the result.

### The program offers the following menu with the corresponding functionalities:
#### 1) add - this option enables to create new flashcard and store it in the local memory (if the flashcard with the same term or the same definition already exists in the memory, the new one will NOT be added),
#### 2) remove - this option enables to remove flashcard from the local memory (if such flashcard exists in local memory),
#### 3) import - this option enables to read in flashcards from the text file and add them to already contained in local memory. If loaded flashcard was already present in local memory, then flashcard in local memory will be updated by that one from the file. This option requires to provide filename (file requires column with the term and column with the definition delimited by tab. Third column containing number of mistakes for corresponding flashcard is optional),
#### 4) export - this option enables to save flashcards, contained in local memory, to the text file. It requires to provide filename,
#### 5) ask - this option enables to play with flashcards. User provides number how many times he wants to be asked by program. Then program displays the term and expects user's answer in order to compare with correct definition. After that appropriate result is displayed. Program displays terms in pseudo random order based on pseudo random number generator,
#### 6) exit - this option exits the program,
#### 7) log - this option enables to save in the file all inputs provided to the console by the user. It requires to provide filename,
#### 8) hardest card - this option enables to display statistics (card/cards with highest number of mistakes made by the user). If numbers of mistakes for corresponding cards are present in the local memory then they will be saved in the file together with term and definition during export to file. If numbers of mistakes for corresponding flashcards are present in the file, then after importing from file, mistakes will be read in to statistics and available there,
#### 9) reset stats - this option enables to remove statistics (mistakes for corresponding flashcards) from local memory,
#### 10) count - this option displays number of cards currently available in the local memory.

#### Additionally, program is able to read in two command-line arguments (-import, -export) and react accordingly, without user's interaction

## Examples
### Example 1
#### Input:
#### java Main -import capitals.txt

#### Due to this command, program automatically reads in flashcards from provided file, before displaying menu.

### Example 2
#### Input:
#### java Main -export animals.txt

#### Due to this command, program automatically saves flashcards into the provided file, after exiting from the menu.

### Example 3
#### Input:
#### java Main -export animals.txt -import old_animals.txt

#### Due to this command, program automatically reads in flashcards from provided file (old_animals.txt), before displaying menu, and it automatically saves flashcards into the provided file (animals.txt), after exiting from the menu.