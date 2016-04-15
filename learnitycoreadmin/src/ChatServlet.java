import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.util.Vector;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ChatServlet extends HttpServlet
{
    public static String port;
    public SCServer server;
    public boolean startRtp;
    
    public ChatServlet() {
        this.startRtp = true;
    }
    
    public void init(final ServletConfig config) throws ServletException {
        super.init(config);
        ChatServlet.port = config.getInitParameter("port");
        System.out.println("**************port = " + ChatServlet.port);
        final String[] args = { "-nographics", "-portnumber", ChatServlet.port };
        this.getServletContext().setAttribute("port", (Object)ChatServlet.port);
        try {
            this.server = new SCServer(args);
        }
        catch (Exception exp) {
            exp.printStackTrace();
        }
    }
    
    public String findChatRoom(final String roomName) {
        int returnRoom = 0;
        SCChatRoom tempRoom = null;
        for (int count = 0; count < this.server.chatRooms.size(); ++count) {
            tempRoom = (SCChatRoom) this.server.chatRooms.elementAt(count);
            if (tempRoom.name.equals(roomName)) {
                returnRoom = tempRoom.clients.size();
                break;
            }
        }
        return "" + returnRoom;
    }
    
    public void doGet(final HttpServletRequest request, final HttpServletResponse response) throws IOException, ServletException {
        try {
            if (this.startRtp) {
                final HttpSession mysession = request.getSession(false);
                String ipserv = InetAddress.getByName(request.getServerName()).toString();
                final String hname = request.getServerName();
                System.out.println("****ServerName************ServerName*******************" + hname);
                System.out.println("****ipserv************ipserv*******************" + ipserv);
                final int i = ipserv.lastIndexOf("/");
                final int k = ipserv.length();
                ipserv = ipserv.substring(i + 1, k);
            }
            this.startRtp = false;
            final ObjectOutputStream out = new ObjectOutputStream((OutputStream)response.getOutputStream());
            final ObjectInputStream objin = new ObjectInputStream((InputStream)request.getInputStream());
            response.setHeader("Pragma", "no-cache");
            response.setHeader("Cache-Control", "no-cache");
            response.setDateHeader("Expires", 0L);
            final Vector vContent = (Vector)objin.readObject();
            final Vector v = new Vector();
            objin.close();
            for (int j = 0; j < vContent.size(); ++j) {
                v.addElement(this.findChatRoom((String) vContent.elementAt(j)));
            }
            out.writeObject(v);
            out.flush();
            out.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void doPost(final HttpServletRequest request, final HttpServletResponse response) throws IOException, ServletException {
        this.doGet(request, response);
    }
    
    public void destroy() {
        System.out.println("Server Shuting Down..............");
        this.server.shutdown();
    }
    
    static {
        ChatServlet.port = null;
    }
}
