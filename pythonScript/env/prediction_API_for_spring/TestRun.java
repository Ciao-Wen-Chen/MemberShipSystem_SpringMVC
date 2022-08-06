import java.io.BufferedReader;
import java.io.InputStreamReader;

import netscape.javascript.JSObject;


class TestRun{
    public static void main(String[] args) throws Exception {
        givenPythonScript_whenPythonProcessInvoked_thenSuccess();
    }

    public static void givenPythonScript_whenPythonProcessInvoked_thenSuccess() throws Exception {

        
        ProcessBuilder processBuilder = new ProcessBuilder("python", "test.py",
        "-i",
        "test.json");
        processBuilder.redirectErrorStream(true);
    
        Process process = processBuilder.start();
        BufferedReader subProcessInputReader = new BufferedReader(new InputStreamReader(process.getInputStream()));

            String line = null;
            while ((line = subProcessInputReader.readLine()) != null)
                System.out.println(line);
        
    }

}