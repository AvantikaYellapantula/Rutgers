#ifndef tokenizer
#define tokenizer

enum STATE {
  DIGIT = 1,
  INT = 2,
  FLOAT = 3,
  ZERO = 4,
  OCTAL = 5,
  HEX = 6,
  WORD = 7,
  L_BRACE = 8,
  R_BRACE = 9,
  WHITE_SPACE = 11,
  ESCAPE_ERROR = 12,
  null = 13,
  OPERATOR = 14
};

enum W_SPACE {
  HORIZONTAL_TAB = 9,
  NEW_LINE = 10,
  VERTICAL_TAB = 11,
  FORM_FEED = 12,
  CARRIAGE_RETURN = 13,
  SPACE = 32
};

struct TokenizerT_ {
  int startIndex;
  int endIndex;
  char *token;
  enum STATE cur;
};

typedef struct TokenizerT_ TokenizerT;

TokenizerT *TKCreate(char *ts);
void TKDestroy(TokenizerT *tk);
char *TKGetNextToken(TokenizerT *tk);
void printState(TokenizerT *tk);

int checkWhiteSpace(int ascii);
int isDigit(int ascii);
int isChar(int ascii);
int isSymbol(int ascii);
int checkError(int ascii);
char *getTokenString(TokenizerT *tk);
void checkNextState(TokenizerT *tk, int ascii);

#endif
