/// IMPORTS ───────────────────────────────────────────────────────────────────────────────────  ///
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;



/// CLASS(): GuessWord ────────────────────────────────────────────────────────────────────────  ///
public class GuessWord {
    /// N: Most static Variables at End of File

    /// METHOD(): main() ─────────────────────────────────────────────────────────────────────── ///
    public static void main(String[] args) {
        /// PROGRAM MESSAGES: SECTION INFO ────────────────────────────────────────────────────  ///
            /// Msg: Project Intro Message ────────────────────────────────────────────────────  ///
            final String introTitle  = PROJECT_NAME + " by " + PROJECT_AUTH;
            final String introSubtitle = PROJECT_FILE + " version " + PROJECT_VERS + " (Date: " + PROJECT_DATE + ")";
            final String introBody   = "This program will generate a random word and ask you to guess it.";
            /// Msg: Color Test ───────────────────────────────────────────────────────────────  ///
            /// Msg: Instructions Message ─────────────────────────────────────────────────────  ///
        /// END PROGRAM MESSAGES ──────────────────────────────────────────────────────────────  ///
        System.out.println(PROJECT_DATE);
    }
    /// END METHOD(): main() ─────────────────────────────────────────────────────────────────── ///

    /// SUBROUTINES: REUSABLE METHODS ───────────────────────────────────────────────────────── ///
        /// SUBROUTINES: FORMATTING ──────────────────────────────────────────────────────────── ///
            /// SUBROUTINE: FORMATTING_Section_bodyLine(String str) ──────────────────────────── ///
            public static StringBuilder FORMATTING_Section_bodyLine(String str) {
                /// N: Return a StringBuilder with top/bot(tom)/div/bl(an)k options
                StringBuilder bodyLine  = new StringBuilder();

                String borderLeft;
                String borderRight;
                String lineFill         = BORDER_HH;                            /// NOTE: Default fill

                switch (str) {
                    case TOP:
                        borderLeft      = BORDER_TL;
                        borderRight     = BORDER_TR;
                        break;
                    case BOTTOM:
                        borderLeft      = BORDER_BL;
                        borderRight     = BORDER_BR;
                        break;
                    case DIV:
                        borderLeft      = BORDER_DL;
                        borderRight     = BORDER_DR;
                        break;
                    case "BLANK":
                        borderLeft      = BORDER_VV;
                        borderRight     = BORDER_VV;
                        lineFill        = " ";                                  /// NOTE: Replace with whitespace
                        break;
                }
                bodyLine.append(BORDER_DL);
                bodyLine.append(BORDER_HH.repeat(TOTAL_LN_LEN));
                bodyLine.append(BORDER_DR);
                return bodyLine;
            }

            /// SUBROUTINE: FORMATTING_Section_bodyMsg(String msg) ───────────────────────────── ///
            public static StringBuilder FORMATTING_Section_bodyMsg(String msg) {
                /// N: FORMATTING_Section_bodyMsg(String msg) Overwrite a blank,
                /// N: bordered line with a string, split lines if needed */

                /// N: Create StringBuilders
                StringBuilder strLine = new StringBuilder();
                StringBuilder oldStrLine = new StringBuilder(msg);

                /// N: Prepopulate blankLine
                final String blankLine = FORMATTING_Section_bodyLine(BLANK).toString();

                /// N: Split msg into lines if necessary ─────────────────────────────────────── ///
                int msgLength           = msg.length();                         /// N: Get message length
                String newLineChar      = "/n";                                 /// N: New line escape code
                int insertLen           = newLineChar.length();                 /// N: "/n" counts as one char
                boolean tooLong         = false;                                /// N: true will split text, def=false
                int i                   = 0;                                    /// N: i=Index, default=0
                int subStrStart_i       = ONE_BORDER_PAD;                       /// N: Start char position index after border and padding
                int subStrEnd_i         = msgLength;                            /// N: End charPos[i]] def=strLength, overwrite if split
                int line_0              = 1;                                    /// N: First line, def=1
                int line_n              = 1;                                    /// N: Final line, def=1

                /// N: Iterate splits ────────────────────────────────────────────────────────── ///
                
                if (msgLength > BODY_LN_LEN) {                                  /// N: Determine lines and flag split
                    tooLong = true;                                             /// N: Set flag if needs split
                    line_n = (int) Math.floor(                                  /// N: Always round down
                            (double) msgLength) + line_0;                       /// N: Count any partial line
                }


                strLine.append(msg);
                for (i = 0; i < line_n; i++) {
                    if (tooLong) {
                        
                        /// NOTE: Logic/math:                                   ///
                        /// N: Shift(i=0) == lineNumber(i+1) * (line length) -1 ///
                        /// N: Shift == (0+1) *  -1                             ///
                        /// N: Shift == 1 * 69 = 69                             ///
                        /// N: Insert "\n" at position 69                       ///
                        int lineNo      = i + 1;                                /// N: Index to line
                        int shift       = (lineNo * insertLen);                 /// N: Line to final char distance
                        int shiftIndex  = shift - 1;                            /// N: Line to index
                            subStrEnd_i = BODY_LN_LEN + shiftIndex;             /// N: Set end index
                    }
                }
                return strLine;
            }
            /// SUBROUTINE: FORMATTING_Location_lastWhiteSpace ───────────────────────────────── ///
            public static ArrayList FORMATTING_Location_allWhiteSpace(String str) {
                /// N: Return an ArrayList of all whitespace positions in a string
                ArrayList<Integer> whiteSpacePosArray = new ArrayList<>();      /// N: Create ArrayList
                int p   = 0;                                                    /// N: Index position

                /// N: Iterate through string, add whitespace positions to ArrayList
                boolean isStrLengthRemaining    = true;                         /// N: Flag for loop 
                for (p = 0; isStrLengthRemaining; p++) {                        /// N: Terminates when flag tripped
                    String  searchStr           = " ";                          /// N: Search for whitespace " "
                            p                   = str.indexOf(searchStr, p);    /// N: Search from index p (last pos)
                    if (p == -1) {                                              /// N: If no more whitespace (-1), break
                        isStrLengthRemaining = false;                           /// N: Flip flag to break infinite loop
                        break;                                                  /// N: Break loop before writing -1 to ArrayList
                    }                                                           /// N: End if
                    whiteSpacePosArray.add(p);                                  /// N: Add whitespace position to ArrayList
                }                                                               /// N: End for loop    

                /// N: Iterate through string, add linebreak positions to ArrayList
                isStrLengthRemaining            = true;                         /// N: Reset loop flag 
                for (p = 0; isStrLengthRemaining; p++) {                        /// N: Terminates when flag tripped
                    String  searchStr           = "\n";                         /// N: Search for linebreak \n
                            p                   = str.indexOf(searchStr, p);    /// N: Search from index p (last pos) 
                    if (p == -1) {                                              /// N: If no more linebreak (-1), break
                        isStrLengthRemaining = false;                           /// N: Flip flag to break infinite loop
                        break;                                                  /// N: Break loop before writing -1 to ArrayList
                    }                                                           /// N: End if                      
                    whiteSpacePosArray.add(p);                                  /// N: Add linebreak position to ArrayList
                    }                                                           /// N: End for loop

                whiteSpacePosArray.sort(Comparator.naturalOrder());             /// N: Sort ArrayList from low => high
                return whiteSpacePosArray;                                      /// N: Return ArrayList
            }

            /// SUBROUTINES: FORMATTING - Console Control ────────────────────────────────────── ///
                /// SUBROUTINE - FORMATTING: CONSOLE_clearTerm() ─────────────────────────────── ///
                public static void CONSOLE_clearTerm() {
                    /// N: Clear console to prep for new section
                    System.out.print("\033[H" + "\033[2J");
                }

            /// END SUBROUTINES - FORMATTING: Console Control ────────────────────────────────── ///
        /// END SUBROUTINES: FORMATTING ──────────────────────────────────────────────────────── ///
        /// SUBROUTINES: MATH ────────────────────────────────────────────────────────────────── ///
            /// SUBROUTINE: MATH_randomInt(int min, int max) ────────────────────────────────── ///
            public static int MATH_Compare_nearestIntUnderMax(ArrayList intArrayList, int maxInt) {
                /// N: Return the nearest number from a list that is not more than maxInt
                int arrayListSize = intArrayList.size();
                for (int i = 0; i < arrayListSize; i++) {
                    int intInList = (int)intArrayList.get(i);
                    if (intInList <= maxInt) {
                        maxInt = (int)intInList;
                    }
                }
                return (int)maxInt;
            }
        /// END SUBROUTINES: MATH ────────────────────────────────────────────────────────────── ///
    /// END SUBROUTINES ──────────────────────────────────────────────────────────────────────── ///
    /// G ────────────────────────────────────────────────────────────────────────────────────── ///
    /// GROUP: GLOBAL VARIABLES ──────────────────────────────────────────────────────────────── ///
    /// G ────────────────────────────────────────────────────────────────────────────────────── ///
        /// GLOBAL VAR: SCANNER (consoleIn)───────────────────────────────────────────────────── ///
        static Scanner consoleIn = new Scanner(System.in);                      /// N: Allow input in all methods
        /// GLOBAL VAR: BORDER STYLES (UNICODE) ──────────────────────────────────────────────── ///
        static final String BORDER_TL   = "╔";                                  /// N: TOP-LEFT BORDER
        static final String BORDER_TR   = "╗";                                  /// N: TOP-RIGHT BORDER
        static final String BORDER_BL   = "╚";                                  /// N: BOTTOM-LEFT BORDER
        static final String BORDER_BR   = "╝";                                  /// N: BOTTOM-RIGHT BORDER
        static final String BORDER_HH   = "═";                                  /// N: HORIZONTAL BORDER
        static final String BORDER_VV   = "║";                                  /// N: VERTICAL BORDER
        static final String BORDER_DD   = "╦";                                  /// N: DIVIDER TO-DOWN BORDER
        static final String BORDER_DU   = "╩";                                  /// N: DIVIDER TO-UP BORDER
        static final String BORDER_DL   = "╠";                                  /// N: DIVIDER TO-LEFT BORDER
        static final String BORDER_DR   = "╣";                                  /// N: DIVIDER TO-RIGHT BORDER
        /// GLOBAL VAR: COLOR CODES (UNICODE)─────────────────────────────────────────────────── ///
        static final String RESET       = "\u001B[0m";                          /// N: Must reset console
        static final String BLACK       = "\u001B[30m";                         /// N: Unicode color
        static final String RED         = "\u001B[31m";                         /// N: Unicode color
        static final String GREEN       = "\u001B[32m";                         /// N: Unicode color
        static final String YELLOW      = "\u001B[33m";                         /// N: Unicode color
        static final String BLUE        = "\u001B[34m";                         /// N: Unicode color
        static final String MAGENTA     = "\u001B[35m";                         /// N: Unicode color
        static final String CYAN        = "\u001B[36m";                         /// N: Unicode color
        static final String DRK_GR      = "\u001B[234m";                        /// N: Unicode color
        static final String RAINBOW     = "RAINBOW";                            /// N: Requires function
        /// GLOBAL VAR: CONSOLE CONTROL CODES ────────────────────────────────────────────────── ///
        static final String CRS_U       = "\033[1A";                            /// N: Cursor control
        static final String CRS_D       = "\033[1B";                            /// N: Cursor control
        static final String CRS_R       = "\033[1C";                            /// N: Cursor control
        static final String CRS_L       = "\033[1D";                            /// N: Cursor control
        /// GLOBAL VAR: LINE STYLES ──────────────────────────────────────────────────────────── ///
        static final String TOP         = "TOP";                                /// N: Section Top
        static final String BOTTOM      = "BOTTOM";                             /// N: Section Bottom
        static final String BLANK       = "BLANK";                              /// N: Blank, Side Borders
        static final String DIV         = "DIV";                                /// N: Horizontal Divider
        static final String EMPTY       = "EMPTY";                              /// N: Nothing at all
        /// GLOBAL VAR: OUTPUT DIMENSIONS ────────────────────────────────────────────────────── ///
        static final int TOTAL_LN_LEN   = 70;                                   /// N: Section width
        static final int ONE_BORDER_PAD = 2;                                    /// N: 4 chars = "| "+{body}+" |"
        static final int BORDER_PAD     = 2 * ONE_BORDER_PAD;                   /// N: 4 chars = "| "+{body}+" |"
        static final int BODY_LN_LEN    = TOTAL_LN_LEN - BORDER_PAD;            /// N: Writeable line lenght
        /// GLOBAL VAR: PROJECT DETAILS ──────────────────────────────────────────────────────── ///
        /// NOTE: Static project details
        static final String PROJECT_NAME = "Guess-a-Word Game";                 /// N: Project name
        static final String PROJECT_DESC = "This program allows the user to " + /// N: Project description
                                           "guess a word that is randomly " + 
                                           "selected from a list of words.";
        static final String PROJECT_FILE = "GuessWord.java";                    /// N: Project file name
        static final String PROJECT_AUTH = "Charles Morman";                    /// N: Project author
        static final String PROJECT_DATE = "6/25/2023";                         /// N: Project latest edit date
        static final String PROJECT_VERS = "3.1.1741";                          /// N: Project version (Major.Minor.BuildTime(24hr))
    /// G ────────────────────────────────────────────────────────────────────────────────────── ///
    /// END GROUP GLOBAL VARIABLES────────────────────────────────────────────────────────────── ///
    /// G ────────────────────────────────────────────────────────────────────────────────────── ///
        }
/// END CLASS() ──────────────────────────────────────────────────────────────────────────────── ///