Feature: Processing batches of booking requests.

  Scenario: Given following batch of meeting request
    Given the batch meeting request
    When the meeting requests are as below
          """
          0900 1730
          2011-03-17 10:17:06 EMP001
          2011-03-21 09:00 2
          2011-03-16 12:34:56 EMP002
          2011-03-21 09:00 2
          2011-03-16 09:28:23 EMP003
          2011-03-22 14:00 2
          2011-03-17 11:23:45 EMP004
          2011-03-22 16:00 1
          2011-03-15 17:29:12 EMP005
          2011-03-21 16:00 3
          """
    Then the output of the request should be
          """
          2011-03-21
          09:00 11:00 EMP002
          2011-03-22
          14:00 16:00 EMP003
          16:00 17:00 EMP004
          """

