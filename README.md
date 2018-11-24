# Side Project - GUI Chat App 
Java FX 

For this project, I will implement a simple chat application with 3 users. 

1. Users will have an opportunity to join chat or leave it. 
2. After user joined chat he\she can leave a message which will appear in every chat window for every user. 
3. There will be a main chat window where will be information about which user joined\left chat. 

# User window 
1. User window should include 3 buttons: leave (to leave chat), join (to join chat) and post (to send a message). 
2. There should be text input field to write a message and text field to see all messages. 
3. Users can’t post a message before joining. 
4. Messages will appear only in that chat windows where users joined. 

# Main window 
1. Main window should include information who joined\left chat. 
2. Data+UserName+left\joined. Like special logs for chat. 
3. By closing main window the whole app can be closed. 

#Implementation 
1. Chat should be multithreaded with peer to TCP server connection. 
2. Chat will work using laptop's IP address and on port 7070. 
3. All information (ip address, port and so on) will be provided through config file. 
4. Adaption check implemented by ping-requests 
5. Server connection will use sockets with TCP/IP protocol 
6. Exchange messages format: String MESSAGE author text //JOIN author room //LEAVE author room 
7. Public API is not allowed. API should be through sockets in text format 
8. Users will send each other string messages 

#Client 
1. Establishes server connectivity and messaging. 
2. GUI. 
3. Display the list of rooms and connect to the selected 

#Server 
1. New Users connection 
2. Forwarding messages to everyone in the channel.