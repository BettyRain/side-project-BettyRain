# Side Project - GUI Chat App
Netbeans GUI Builder

For this project, I will implement a simple chat application with 3 users.
1. Users will have an opportunity to join chat or leave it.
2. After user joined chat he\she can leave a message which will appear in every chat window for every user.
3. There will be a main chat window where will be information about which user joined\left chat.

# User window
1. User window should include 3 buttons: leave (to leave chat), join (to join chat) and post (to send a message).
2. There should be text input field to write a message and text field to see all messages.
3. User message should appear on the left and other user’s messages should be on the right. Other user’s messages will be in the chat window with “UserName says:” and then message. 
4. Each user window will include information how many users joined this chat.
5. Messages will appear only in that chat windows where users joined.
6. Users can’t post a message before joining.

# Main window
1. Main window should include information who joined\left chat.
2. Data+UserName+left\joined. Like special logs for chat.
3. By closing main window the whole app can be closed.

# Implementation
Chat should be multithreaded with peer to TCP server connection.
Chat will work using laptop's IP address and on port 7070.
All information (ip address, port and so on) will be provided through config file.
