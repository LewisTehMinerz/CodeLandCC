package org.luaj.vm2.parser;


public interface LuaParserConstants {

   int EOF = 0;
   int COMMENT = 17;
   int LONGCOMMENT0 = 18;
   int LONGCOMMENT1 = 19;
   int LONGCOMMENT2 = 20;
   int LONGCOMMENT3 = 21;
   int LONGCOMMENTN = 22;
   int LONGSTRING0 = 23;
   int LONGSTRING1 = 24;
   int LONGSTRING2 = 25;
   int LONGSTRING3 = 26;
   int LONGSTRINGN = 27;
   int AND = 29;
   int BREAK = 30;
   int DO = 31;
   int ELSE = 32;
   int ELSEIF = 33;
   int END = 34;
   int FALSE = 35;
   int FOR = 36;
   int FUNCTION = 37;
   int IF = 38;
   int IN = 39;
   int LOCAL = 40;
   int NIL = 41;
   int NOT = 42;
   int OR = 43;
   int RETURN = 44;
   int REPEAT = 45;
   int THEN = 46;
   int TRUE = 47;
   int UNTIL = 48;
   int WHILE = 49;
   int NAME = 50;
   int NUMBER = 51;
   int FLOAT = 52;
   int DIGIT = 53;
   int EXP = 54;
   int HEX = 55;
   int HEXDIGIT = 56;
   int STRING = 57;
   int CHARSTRING = 58;
   int QUOTED = 59;
   int DECIMAL = 60;
   int UNICODE = 61;
   int CHAR = 62;
   int LF = 63;
   int DEFAULT = 0;
   int IN_COMMENT = 1;
   int IN_LC0 = 2;
   int IN_LC1 = 3;
   int IN_LC2 = 4;
   int IN_LC3 = 5;
   int IN_LCN = 6;
   int IN_LS0 = 7;
   int IN_LS1 = 8;
   int IN_LS2 = 9;
   int IN_LS3 = 10;
   int IN_LSN = 11;
   String[] tokenImage = new String[]{"<EOF>", "\" \"", "\"\\t\"", "\"\\n\"", "\"\\r\"", "\"\\f\"", "\"--[[\"", "\"--[=[\"", "\"--[==[\"", "\"--[===[\"", "<token of kind 10>", "\"[[\"", "\"[=[\"", "\"[==[\"", "\"[===[\"", "<token of kind 15>", "\"--\"", "<COMMENT>", "\"]]\"", "\"]=]\"", "\"]==]\"", "\"]===]\"", "<LONGCOMMENTN>", "\"]]\"", "\"]=]\"", "\"]==]\"", "\"]===]\"", "<LONGSTRINGN>", "<token of kind 28>", "\"and\"", "\"break\"", "\"do\"", "\"else\"", "\"elseif\"", "\"end\"", "\"false\"", "\"for\"", "\"function\"", "\"if\"", "\"in\"", "\"local\"", "\"nil\"", "\"not\"", "\"or\"", "\"return\"", "\"repeat\"", "\"then\"", "\"true\"", "\"until\"", "\"while\"", "<NAME>", "<NUMBER>", "<FLOAT>", "<DIGIT>", "<EXP>", "<HEX>", "<HEXDIGIT>", "<STRING>", "<CHARSTRING>", "<QUOTED>", "<DECIMAL>", "<UNICODE>", "<CHAR>", "<LF>", "\";\"", "\"=\"", "\",\"", "\".\"", "\":\"", "\"(\"", "\")\"", "\"[\"", "\"]\"", "\"...\"", "\"{\"", "\"}\"", "\"+\"", "\"-\"", "\"*\"", "\"/\"", "\"^\"", "\"%\"", "\"..\"", "\"<\"", "\"<=\"", "\">\"", "\">=\"", "\"==\"", "\"~=\"", "\"#\""};


}
