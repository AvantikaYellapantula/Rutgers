#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include "tokenizer.h"

/*
 * Initialize The TokenizerT Struct Values To Enter The Main Loop
*/
TokenizerT *TKCreate(char *ts) {
  // Initialize All Variables
  TokenizerT *token = malloc(sizeof(struct TokenizerT_));
  token->token = ts; 
  token->startIndex = 0;
  token->endIndex = 0;
 
  // Obtain Standard Integer Value Of Character From ASCII Table
  int ascii = ((int) ts[0]);

  // Determine First Char Index State And Set To Cur In TokenizerT Struct
  token->cur = (checkWhiteSpace(ascii) == 1) ? WHITE_SPACE : null;
  token->cur = (checkError(ascii) == 1) ? ESCAPE_ERROR : token->cur;
 
  if(token->cur == null) {
	 checkNextState(token, ascii);
  }

  // Enter Main Loop To Tokenize The String
  TKGetNextToken(token);

  return token;
}

/*
 * Free Memory Of TokenizerT Pointer
*/
void TKDestroy(TokenizerT *tk) {
  free(tk);
}

/*
 * Main Loop To Tokenize Agrument String
 * Index Is The Int Value Of The Current Char Location In the Argument String
 * Check Is The Char Value Of the Current Index
 * Ascii Is The Integer Value From The Standard ASCII Table
*/
char *TKGetNextToken(TokenizerT *tk) {
  // Initialize Variables
  char check;
  int ascii;
  int index = tk->startIndex + 1;

  // Main Loop
  while(index <= strlen(tk->token)) {
    check = tk->token[index];
    ascii = ((int)check);
	
    // Depending On The Current State
    // The Current Character Will Be Handle With Different Conditions
    switch(tk->cur) {
      case DIGIT:
		    if (ascii == 46) {
			    tk->cur = FLOAT;
			    tk->endIndex = index;
			    checkNextState(tk, ascii); 
		    } else if (isDigit(ascii) == 0) {
				  if (strlen(getTokenString(tk)) > 1){
					  printf("int %s \n", getTokenString(tk));
          } else {
					  printf("digit %s \n", getTokenString(tk));
          }
			     tk->startIndex = index;
			    checkNextState(tk, ascii);
        } else {
			    tk->endIndex++;
        }
        break;
      case INT: 
        break;
      case FLOAT:
		    if (checkWhiteSpace(ascii) == 1 || index == strlen(tk->token)) {
			    printf("float %s \n", getTokenString(tk));
			    tk->startIndex = index;
			    checkNextState(tk, ascii);
          } else {
			      tk->endIndex++;
          }
        break;
      case ZERO:
		    if (checkWhiteSpace(ascii) == 1 || index == strlen(tk->token)) {
			    printf("zero %s \n", getTokenString(tk));
			    tk->startIndex = index;
			    tk->endIndex = index;
			    checkNextState(tk, ascii); 
		    }else if (ascii == 46) {
			    tk->cur = FLOAT;
			    tk->endIndex = index;
			    checkNextState(tk, ascii); 
		    }else if (isDigit(ascii)) {
			    tk->cur = OCTAL;
			    tk->startIndex = index-1;
			    tk->endIndex = index;
			    checkNextState(tk, ascii); 
		    }else if (ascii == 88 || ascii == 120) {
			    tk->cur = HEX;
			    tk->startIndex = index-1;
			    tk->endIndex = index;
			    checkNextState(tk, ascii); 
		    }
        break;
      case OCTAL:
		    if (checkWhiteSpace(ascii) == 1 || index == strlen(tk->token)) {
			    printf("octal %s \n", getTokenString(tk));
			    tk->startIndex = index;
			    tk->endIndex = index;
			    checkNextState(tk, ascii); 
		    } else {
			    tk->endIndex++;
        }
        break;
      case HEX:
		    if (checkWhiteSpace(ascii) == 1 || index == strlen(tk->token)) {
			    printf("hex %s \n", getTokenString(tk));
			    tk->startIndex = index;
			    tk->endIndex = index;
			    checkNextState(tk, ascii); 
		    } else {
			    tk->endIndex++;
        }
        break;
      case WORD:
        if (isChar(ascii) == 0) {
          printf("word %s \n", getTokenString(tk));
          tk->startIndex = tk->endIndex + 1;
          checkNextState(tk, ascii);
        } else {
          tk->endIndex++;
        }
        break;
      case L_BRACE:
        printf("left brace [\n");
        tk->startIndex = index;
        tk->endIndex = index;
        checkNextState(tk, ascii); 
        break;
      case R_BRACE:
        printf("right brace ]\n");
        tk->startIndex = index;
        tk->endIndex = index;
        checkNextState(tk, ascii); 
        break;
      case WHITE_SPACE:
        if (checkWhiteSpace(ascii) == 0) {
			    tk->startIndex = index;
			    tk->endIndex = index;
			    checkNextState(tk, ascii); 
        } else {
			    tk->endIndex = index;
        }
        break;
      case ESCAPE_ERROR:
        switch((int) tk->token[(index-1)]){
          case 0: printf("null "); break;
          case 1: printf("start-of-heading "); break;
          case 2: printf("start-of-text "); break;
          case 3: printf("end-of-text "); break;
          case 4: printf("end-of-transmission "); break;
          case 5: printf("enquiry "); break;
          case 6: printf("acknowledge "); break;
          case 7: printf("bell "); break;
          case 8: printf("backspace "); break;
          case 14: printf("shift-out "); break;
          case 15: printf("shift-in "); break;
          case 16: printf("data-link-escape "); break;
          case 17: printf("device-control-1"); break;
          case 18: printf("device-control-2 "); break;
          case 19: printf("device-control-3 "); break;
          case 20: printf("device-control-4 "); break;
          case 21: printf("negative-acknowledge "); break;
          case 22: printf("synchronous-idle "); break;
          case 23: printf("end-of-trans.-block "); break;
          case 24: printf("cancel "); break;
          case 25: printf("end-of-medium "); break;
          case 26: printf("substitute "); break;
          case 27: printf("escape "); break;
          case 28: printf("file-separator "); break;
          case 29: printf("group-separator "); break;
          case 30: printf("record-separator "); break;
          case 31: printf("unti-separator "); break;
        } 
        printf("[0x%x]\n", ((int) tk->token[(index-1)]));
        tk->startIndex = index;
        tk->endIndex = index;
        checkNextState(tk, ascii);
        break;
	    case null:
        break;
      case OPERATOR:
        if ((tk->token[tk->startIndex]) && (tk->token[tk->startIndex+1]) == '+'){
          printf("increment ++\n");
          tk->startIndex = index + 1;
          tk->endIndex = index + 1;
          checkNextState(tk, (int)tk->token[tk->startIndex]); 
        } 
        else if ((tk->token[tk->startIndex]) == '-' && (tk->token[tk->startIndex+1]) == '-'){
          printf("decrement --\n");
          tk->startIndex = index + 1;
          tk->endIndex = index + 1;
          checkNextState(tk, (int)tk->token[tk->startIndex]); 
        } 
        else if ((tk->token[tk->startIndex]) == '-' && (tk->token[tk->startIndex+1]) == '='){
          printf("minusequals -=\n");
          tk->startIndex = index + 1;
          tk->endIndex = index + 1;
          checkNextState(tk, (int)tk->token[tk->startIndex]); 
        } 
        else if ((tk->token[tk->startIndex]) == '+' && (tk->token[tk->startIndex+1]) == '='){
          printf("plusequals +=\n");
          tk->startIndex = index + 1;
          tk->endIndex = index + 1;
          checkNextState(tk, (int)tk->token[tk->startIndex]); 
        } 
        else if ((tk->token[tk->startIndex]) == '=' && (tk->token[tk->startIndex+1]) == '='){
          printf("equals ==\n");
          tk->startIndex = index + 1;
          tk->endIndex = index + 1;
          checkNextState(tk, (int)tk->token[tk->startIndex]); 
        } 
        else if ((tk->token[tk->startIndex]) == '*' && (tk->token[tk->startIndex+1]) == '='){
          printf("multiplyequals /=\n");
          tk->startIndex = index + 1;
          tk->endIndex = index + 1;
          checkNextState(tk, (int)tk->token[tk->startIndex]); 
        } 
        else if ((tk->token[tk->startIndex]) == '/' && (tk->token[tk->startIndex+1]) == '='){
          printf("divideequals /=\n");
          tk->startIndex = index + 1;
          tk->endIndex = index + 1;
          checkNextState(tk, (int)tk->token[tk->startIndex]); 
        } 
        else if ((tk->token[tk->startIndex]) == '%' && (tk->token[tk->startIndex+1]) == '='){
          printf("modulusequals \n");
          tk->startIndex = index + 1;
          tk->endIndex = index + 1;
          checkNextState(tk, (int)tk->token[tk->startIndex]); 
        } 
        else if ((tk->token[tk->startIndex]) == '&' && (tk->token[tk->startIndex+1]) == '='){
          printf("andassignment &=\n");
          tk->startIndex = index + 1;
          tk->endIndex = index + 1;
          checkNextState(tk, (int)tk->token[tk->startIndex]); 
        } 
        else if ((tk->token[tk->startIndex]) == '^' && (tk->token[tk->startIndex+1]) == '='){
          printf("exclusiveorassignment ^=\n");
          tk->startIndex = index + 1;
          tk->endIndex = index + 1;
          checkNextState(tk, (int)tk->token[tk->startIndex]); 
        } 
        else if ((tk->token[tk->startIndex]) == '|' && (tk->token[tk->startIndex+1]) == '='){
          printf("inclusiveorassignment |=\n");
          tk->startIndex = index + 1;
          tk->endIndex = index + 1;
          checkNextState(tk, (int)tk->token[tk->startIndex]); 
        } 
        else if ((tk->token[tk->startIndex]) == '!' && (tk->token[tk->startIndex+1]) == '='){
          printf("notequal !=\n");
          tk->startIndex = index + 1;
          tk->endIndex = index + 1;
          checkNextState(tk, (int)tk->token[tk->startIndex]); 
        } 
        else if ((tk->token[tk->startIndex]) == '>' && (tk->token[tk->startIndex+1]) == '='){
          printf("greatthenequal >=\n");
          tk->startIndex = index + 1;
          tk->endIndex = index + 1;
          checkNextState(tk, (int)tk->token[tk->startIndex]); 
        } 
        else if ((tk->token[tk->startIndex]) == '<' && (tk->token[tk->startIndex+1]) == '='){
          printf("lessthenequals !=\n");
          tk->startIndex = index + 1;
          tk->endIndex = index + 1;
          checkNextState(tk, (int)tk->token[tk->startIndex]); 
        } 
        else if ((tk->token[tk->startIndex]) == '&' && (tk->token[tk->startIndex+1]) == '&'){
          printf("and &&\n");
          tk->startIndex = index + 1;
          tk->endIndex = index + 1;
          checkNextState(tk, (int)tk->token[tk->startIndex]); 
        } 
        else if ((tk->token[tk->startIndex]) == '|' && (tk->token[tk->startIndex+1]) == '|'){
          printf("or ||\n");
          tk->startIndex = index + 1;
          tk->endIndex = index + 1;
          checkNextState(tk, (int)tk->token[tk->startIndex]); 
        } else if ((tk->token[tk->startIndex]) == '+'){
          printf("plus +\n");
          tk->startIndex = index;
          tk->endIndex = index;
          checkNextState(tk, (int)tk->token[tk->startIndex]); 
        }
        else if ((tk->token[tk->startIndex]) == '-'){
          printf("minus -\n");
          tk->startIndex = index;
          tk->endIndex = index;
          checkNextState(tk, (int)tk->token[tk->startIndex]); 
        }
        else if ((tk->token[tk->startIndex]) == '*'){
          printf("multiply *\n");
          tk->startIndex = index;
          tk->endIndex = index;
          checkNextState(tk, (int)tk->token[tk->startIndex]); 
        }
        else if ((tk->token[tk->startIndex]) == '/'){
          printf("divide /\n");
          tk->startIndex = index;
          tk->endIndex = index;
          checkNextState(tk, (int)tk->token[tk->startIndex]); 
        }
        else if ((tk->token[tk->startIndex]) == '!'){
          printf("not !\n");
          tk->startIndex = index;
          tk->endIndex = index;
          checkNextState(tk, (int)tk->token[tk->startIndex]); 
        }
        else if ((tk->token[tk->startIndex]) == '&'){
          printf("bitwise_and &\n");
          tk->startIndex = index;
          tk->endIndex = index;
          checkNextState(tk, (int)tk->token[tk->startIndex]); 
        }
        else if ((tk->token[tk->startIndex]) == '%'){
          printf("modulus \n");
          tk->startIndex = index;
          tk->endIndex = index;
          checkNextState(tk, (int)tk->token[tk->startIndex]); 
        }
        else if ((tk->token[tk->startIndex]) == '*'){
          printf("pointer *\n");
          tk->startIndex = index;
          tk->endIndex = index;
          checkNextState(tk, (int)tk->token[tk->startIndex]); 
        }
        else if ((tk->token[tk->startIndex]) == '^'){
          printf("bitwise exclusive or ^\n");
          tk->startIndex = index;
          tk->endIndex = index;
          checkNextState(tk, (int)tk->token[tk->startIndex]); 
        }
        else if ((tk->token[tk->startIndex]) == '|'){
          printf("bitwise or (incl) |\n");
          tk->startIndex = index;
          tk->endIndex = index;
          checkNextState(tk, (int)tk->token[tk->startIndex]); 
        }
        else if ((tk->token[tk->startIndex]) == '>'){
          printf("greaterthen >\n");
          tk->startIndex = index;
          tk->endIndex = index;
          checkNextState(tk, (int)tk->token[tk->startIndex]); 
        }
        else if ((tk->token[tk->startIndex]) == '<'){
          printf("lessthen <\n");
          tk->startIndex = index;
          tk->endIndex = index;
          checkNextState(tk, (int)tk->token[tk->startIndex]); 
        }
        else if ((tk->token[tk->startIndex]) == '~'){
          printf("complement ~\n");
          tk->startIndex = index;
          tk->endIndex = index;
          checkNextState(tk, (int)tk->token[tk->startIndex]); 
        }
        else if ((tk->token[tk->startIndex]) == '='){
          printf("assignment =\n");
          tk->startIndex = index;
          tk->endIndex = index;
          checkNextState(tk, (int)tk->token[tk->startIndex]); 
        }
        break;
    }
    index++;   
  }
 
  return 0;
}

/*
 * Check If Ascii value of current char
 * Equals To Any Of The Possible White Space Characters
 * Described In The PDF Assignment
 * Return True = 1 Otherwise False = 0
*/
int checkWhiteSpace(int ascii) {
  int boolean = 0;

  switch(ascii){
    case (int) HORIZONTAL_TAB: boolean = 1; break;
    case (int) VERTICAL_TAB: boolean = 1; break;
    case (int) FORM_FEED: boolean = 1; break;
    case (int) CARRIAGE_RETURN: boolean = 1; break;
    case (int) SPACE: boolean = 1; break;
    case (int) NEW_LINE: boolean = 1; break;
  }

  return boolean;
}

/*
 * If the current Character's ascii value equals 0-9
 * Return True = 1 Otherwise False = 0
*/
int isDigit(int ascii) {
  if(ascii >= 48 && ascii <= 57) {
    return 1;
  }
  return 0;
}

/*
 * If The current Character's ascii value equals A-Z or a-z
 * Return True = 1 Otherwise False = 0 
*/
int isChar(int ascii) {
  if((ascii >= 65 && ascii <= 90) || (ascii >= 97 && ascii <= 122)) {
    return 1;
  }
  return 0;
}

/*
 * If The Current Character's ascii value is within the range of a symbol
 * Return True = 1 Otherwise False = 0
*/
int isSymbol(int ascii) {
  if((ascii >= 33 && ascii <= 45) || (ascii == 47) || (ascii >= 58 && ascii <= 64) || (ascii >= 91 && ascii <= 96) || (ascii >= 123 && ascii <= 126)){
    return 1;
  }
    return 0;
}

/*
 * Check If Ascii value of current char
 * Equals To Any Of The Possible Error Characters
 * Described In The PDF Assignment
 * Return True = 1 Otherwise False = 0
*/
int checkError(int ascii) {
  if((ascii >= 0 && ascii <= 8) || (ascii >= 14 && ascii <= 31)){
    return 1;
  }
  return 0;
}

/*
 * Returns A Char Pointer
 * Substring The Token From The Original String Argument
 * By Using The Start And End Index Values In TokenizerT Struct
*/
char *getTokenString(TokenizerT *tk) {
  int len = tk->endIndex - tk->startIndex + 1;
  char *tokenString = (char*) malloc(len);
  int i;
  int index = 0;

  for(i = tk->startIndex; i <= tk->endIndex;i++){
    tokenString[index] = tk->token[i];
    index++;
  }
  return tokenString;
}

/*
 * Sets the enum State cur in TokenizerT struct
 * based on the current characters ascii value
*/
void checkNextState(TokenizerT *tk, int ascii){
  if (ascii == 48) {
    tk->cur = ZERO;
  } else if (((ascii >= 48 && ascii <= 57) || (ascii >= 67 && ascii <= 70) || (ascii >= 97 && ascii <= 102) || (ascii == 88) || (ascii == 120)) 
      && tk->cur == HEX) {
    tk->cur = HEX;
  } else if ((ascii >= 48 && ascii <= 55) && tk->cur == OCTAL) {
    tk->cur = OCTAL;
  } else if (((ascii >= 48 && ascii <= 57) || ascii == 45 || ascii == 69 || ascii == 101 || ascii == 43) && tk->cur == FLOAT) {
    tk->cur = FLOAT;
  } else if (ascii >= 48 && ascii <= 57) {
    tk->cur = DIGIT;
  } else if(ascii == 91) {
    tk->cur = L_BRACE;
  } else if(ascii == 93) {
    tk->cur = R_BRACE;
  } else if(checkWhiteSpace(ascii) == 1){
    tk->cur = WHITE_SPACE;
  } else if (isChar(ascii) == 1) {
    tk->cur = WORD;
  } else if (isSymbol(ascii) == 1) {
    tk->cur = OPERATOR;
  }
}

//Main Run Method
int main(int argc, char **argv) {
  //Initialize All Variables
  TokenizerT *token;

  //Check If Agrument Token Exists  
  if(argc <= 1) {
    printf("error\n");
    exit(0);
  }

  token = TKCreate(argv[1]); /* Create Token To Initilize Loop */
  TKDestroy(token); /* Destroy Token Once Loop Is Exited */ 

  return 0;
}
