# LFT_Exam

(Using {0, 1} alphabet)
NoThreeZero.java has a:
- public method scan(String s)method, which recognize a string included in the language composed by sequences of {0, 1}, where 0 never appears 3 times in a row.


(Using {0, ..., 9, A, ..., Z, a, ..., z, _ } alphabet)
JavaIdentifier.java has a:
- public method scan(String s), which recognize a java identifier,
- public method isLetter(char ch), which takes a char as argument and check if it's a letter of the Latin alphabet(both capital and not),
- public method isNumber(char ch), which take a char as argument and check if it's a number(from 0 to 9)
- private method isAlphabet(char ch), which take a char as argument and check if it belongs to the identifier alphabet.

(Using {0, ..., 9, A, ..., Z, a, ..., z} alphabet)
SerialNumber.java has a:
- public method scan(String s), which recognize a sequence of numbers representing student's serial number followed by their surname,
Strings accepted are only those of students belonging to T2 and T3. (T2 = serial number even and starting surname letter in A-K -- T3 = serial number odd and starting surname letter in L-Z)
- private T2T3Logic(char ch), takes a char as arguments and check if it's a letter from Latin alphabet, otherwise string is rejected.
- public static boolean isT3(char ch), take a char as argument and return true if letter is in L-Z,
- public static boolean isT2(char ch), take a char as argument and return true if letter is in A-K.

(Using {0, ..., 9, +, -, e, .} alphabet)
ConstantIdentifier.java has a:
- public method scan(String s), which recognize a mathematic constant in form of (±)x.ye(±)z.w, where the second part (e(±)z.w) can be omitted and x, y, z, w belong to {0, ..., 9}

(Using {/, *, a} alphabet)
CommentIdentifier.java has a:
- public method scan(String s), which recognize if a string is a comment(string starts with /*, ends with */).

(Using {/, *, a} alphabet)
CommentIdentifierExtended.java has a:
- public method scan(String s), which extend CommentIdentifier.java, recognizing string with comments in the middle or with no comments at all;

  
