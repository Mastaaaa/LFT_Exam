# LFT_Exam

#First part: Implementig DFA in java
------------------------------------------------------------

(Using {0, 1} alphabet)<br />
NoThreeZero has a:
- public method scan(String s)method, which recognize a string included in the language composed by sequences of {0, 1}, where 0 never appears 3 times in a row.

(Using {0, ..., 9, A, ..., Z, a, ..., z, _ } alphabet)<br />
JavaIdentifier has a:
- public method scan(String s), which recognize a java identifier,
- public method isLetter(char ch), which takes a char as argument and check if it's a letter of the Latin alphabet(both capital and not),
- public method isNumber(char ch), which take a char as argument and check if it's a number(from 0 to 9)
- private method isAlphabet(char ch), which take a char as argument and check if it belongs to the identifier alphabet.

(Using {0, ..., 9, A, ..., Z, a, ..., z} alphabet)<br />
SerialNumber has a:
- public method scan(String s), which recognize a sequence of numbers representing student's serial number followed by their surname,
Strings accepted are only those of students belonging to T2 and T3. (T2 = serial number even and starting surname letter in A-K -- T3 = serial number odd and starting surname letter in L-Z)
- private T2T3Logic(char ch), takes a char as arguments and check if it's a letter from Latin alphabet, otherwise string is rejected.
- public static boolean isT3(char ch), take a char as argument and return true if letter is in L-Z,
- public static boolean isT2(char ch), take a char as argument and return true if letter is in A-K.

(Using {0, ..., 9, +, -, e, .} alphabet)
ConstantIdentifier has a:
- public method scan(String s), which recognize a mathematic constant in form of (±)x.ye(±)z.w, where the second part (e(±)z.w) can be omitted and x, y, z, w belong to {0, ..., 9}

(Using {/, *, a} alphabet)<br />
CommentIdentifier has a:
- public method scan(String s), which recognize if a string is a comment(string starts with /*, ends with */).

(Using {/, *, a} alphabet)<br />
CommentIdentifierExtended has a:
- public method scan(String s), which extends CommentIdentifier.java, recognizing string with comments in the middle or with no comments at all;



------------------------------------------------------------
#Second part: Implementig a lexer in java. The purpose is to read some text, and get tokens in output for every lexical unit.
------------------------------------------------------------

Token has a:
- public final int tag attribute, representing the name of the token,
- public Token(int t) constructor, initializing this.tag attribute to value of t,
- public String toString() method, formatting Token class for printing.
- Some Token constants for mono-simbol token.

(Every token with more than 1 simbol is composed of an int Tag and an int Attribute)<br />
Word extends Token has a:
- public String lexeme attribute, representing attribute of the token,
- public Word(int tag, String lexeme), initializing this.lexeme attribute to value of lexeme,
- public String toString() method, formatting Word class for printing.
- Some Word constants for keyword and double symbol operators.

  Tag is an enumaration of tags for tokens.

  NumberTok has a:
  Word extends Token has a:
- public String lexeme attribute, representing attribute of the token,
- public NumberTok(int tag, String lexeme), initializing this.lexeme attribute to value of lexeme,
- public String toString() method, formatting NumberTok class for printing.

Lexer.java has a:
- public static int line attribute, which tracks like of the text,
- private char peek attribute, which represent the current char beeing analyzed
- private void readch(BufferReader br) method, which read the next character in the file,
- public Token lexical_scan(BufferReader br) method, which analyze the BufferRead searching for tokens. If it can't convert all text in tokens, method prints "Erroneous character" error and return null.<br />
  (Note that not all aviable chars have a corresponding token implementation, and this lexer does not check correct code structure: "else 5 == print < end" is accepted)
  


