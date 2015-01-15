ArubaSyslog
===========

Series of tools/library to process wifi logs in OMNI-Lab, SJTU, China.
Note: These tools work on the original aruba wifi logs collected in our campus.


Major Utilities
---------------

* `SyslogDumper`:

This project receives Aruba syslogs from server and dumps them into individual
files by day. We archive part of all messages that are related to human
behaviours and network management, e.g., association, authentication, roaming,
IP allocation etc. You can find all message codes in the source codes, and more
information about how to user the program in its documentation file.

* `SyslogAnonymizer`:

This project aims to anonymise the raw syslog data to preserve user privacy and
for network security during data usage. Actions that are performed contains
user identity hashing, location info. abstraction, and the removal of explicit
network infrastructure indications. For usage structure, please refer to its
README file.

* `SyslogCleanser`:

This maven project does some dirty work at the early stage of processing raw
Aruba syslog.  Its funcitons include:

* filtering each log entry from raw lines,
* splitting the daily logs into individual users,
* extracting wifi sessions from user logs and
* a simple one-step program to facilitate splitting and session extraction
together.

Several workflows can be followed for different purposes:

        [rawlogs] --> RawLogFilter --> [filteredlogs]

        [filteredlogs] --> UserSessions --> [sessions]


Small Tools
-----------

* `mark_session_ip.py`:

This script works on the wifi sessions to mark each session with an allocated
IP address.  `mark_batch.sh` helps to process multiple these session files in
a batch mode. Workflow:

        [sessions] --> mark_session_ip.py --> [ipsessions].

* `pure_movement.py`:

This tools aims to generate pure movement information in wifi networks. This
movement data can be used to study user mobility patterns in SJTU WiFi
networks. Workflow:

        [sessions] --> pure_movement.py --> [puremovement]


Contact
--------

Xiaming Chen, SJTU  
chenxm35@gmail.com
