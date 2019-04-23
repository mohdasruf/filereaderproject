Feature: File reader

  Scenario Outline: The information in the files read by the service can be found or not found on the website
    Given the service reads files from directory testfiles
    When the service is evoked
    And registration numbers from each file with <name> is entered into the website inturn
    And the website details for registration <regno> with make of <make> with a colour of <colour> will <matchOrNot> with the value in csv

    Examples:
      | name       | regno   | make    | colour | matchOrNot |
      | file1.csv  | YS59ABZ | BMW     | BLUE   | match      |
      | file2.csv  | GX18HBL | AUDI    | BLACK  | match      |
      | file3.csv  | V765DPR | HYUNDAI | SILVER | not found  |
      | file4.csv  | GJ13EBK | HONDA   | RED    | match      |
      | file5.csv  | LA12GZC | NISSAN  | BLUE   | match      |
      | file6.csv  | GJ13EBX | CITROEN | WHITE  | match      |
      | file7.csv  | YS59ABZ | BMW     | BLUE   | match      |
      | file8.csv  | P1LOT   | BMW     | BLACK  | not found  |
      | file9.csv  | PK66PFF | MINI    | BLUE   | match      |
      | file10.csv | RK66PFF | AUDI    | WHITE  | match      |

  Scenario: The user searches using correctly formated registration plate
    Given I am a user of the website
    When I enter a correct registration number of YS59ABZ
    Then I will receive the correct make as BMW and colour of car as BLUE

  Scenario: The user searches using invalid registration plate
    Given I am a user of the website
    When I enter a correctly formatted invalid registration number of V765DPR
    Then I will be taken to an error page

  Scenario: The user searches using incorrectly formatted registration plate
    Given I am a user of the website
    When I enter a incorrectly formatted registration number of WE343467
    Then I will receive an error messsage on the same page