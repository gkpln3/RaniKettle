package guykap.ranikettle;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

/**
 * This class handles and defines the protocol between the phone and the RaniKettle(TM),
 * This protocol is text based.
 *
 * The purpose of this class is to detach to the communication protocol, this can
 * be implemented using wifi, bluetooth, network sockets over the internet, infra-red,
 * microphone and speech-recognition, RF, GPIOs, I2C, Bluetooth Low Energy, telnet, SSH,
 * SSL, TCPIP, UDP, Raw ethernet, computer vision, WiMax, LiFi and more.
 *
 * Created by gkpln3 on 14/03/16.
 */
public class KettleProtocolManager implements Closeable{

    public KettleProtocolManager(InputStream inputStream, OutputStream outputStream) throws NullPointerException
    {
        isConnected = false;
        if (inputStream == null || outputStream == null)
        {
            throw new NullPointerException();
        }

        m_inputStream = new BufferedReader(new InputStreamReader(inputStream));
        m_outputStream = new BufferedWriter(new OutputStreamWriter(outputStream));
    }

    public void Connect() throws IOException, Exception
    {
        // Do the handshake with the rani_kettle.
        m_outputStream.write("Hello");
        m_outputStream.newLine();

        String response = m_inputStream.readLine();
        if (response != "PerchikPerchik")
        {
            throw new Exception("Device is not RANI_KETTLE");
        }

        // At this point we should be connected to the RaniKettle(TM).
        isConnected = true;
    }

    public void TurnKettleOn() throws IOException
    {
        m_outputStream.write("TurnOn");
        String response = m_inputStream.readLine();
    }

    public void TurnKettleOff() throws IOException
    {
        m_outputStream.write("TurnOff");
        String response = m_inputStream.readLine();
    }

    private boolean isConnected;
    private BufferedReader m_inputStream;
    private BufferedWriter m_outputStream;

    @Override
    public void close() throws IOException {
        m_inputStream.close();
        m_outputStream.close();
    }
}
