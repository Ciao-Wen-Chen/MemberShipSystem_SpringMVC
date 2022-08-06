package com.sdproject.membersystem.prediction;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.SimpleScriptContext;
import java.io.File;
import java.io.FileReader;
import java.io.StringWriter;
import org.python.core.Options;

@Service
@AllArgsConstructor
public class PredictionServiceApacheExec{


    public String startPredict(PredictedData request) throws Exception{
        var matchLevel=String.valueOf(request.getMatchLevel());
        System.out.println(matchLevel);
        var matchName=String.valueOf(request.getMatchName());
        System.out.println(matchName);
        var matchSurface=String.valueOf(request.getMatchSurface());

        var p1= String.valueOf(request.getP1());
        var p1Age=String.valueOf(request.getP1Age());
        var p1Fatigue=String.valueOf(request.getP1Fatigue());
        var p1Hand=String.valueOf(request.getP1Hand());
        var p1Height=String.valueOf(request.getP1Height());
        var p1Id=String.valueOf(request.getP1Id());
        var p1Rank=String.valueOf(request.getP1Rank());
        var p1Point=String.valueOf(request.getP1Point());

        var p2=String.valueOf(request.getP2());
        var p2Age=String.valueOf(request.getP2Age());
        var p2Fatigue=String.valueOf(request.getP2Fatigue());
        var p2Hand=String.valueOf(request.getP2Hand());
        var p2Height=String.valueOf(request.getP2Height());
        var p2Id=String.valueOf(request.getP2Id());
        var p2Rank=String.valueOf(request.getP2Rank());
        var p2Point=String.valueOf(request.getP2Point());

        StringWriter writer = new StringWriter();
        ScriptContext context = new SimpleScriptContext();
        context.setWriter(writer);

        ScriptEngineManager manager = new ScriptEngineManager();
        Options.importSite = false;
        ScriptEngine engine = manager.getEngineByName("python");
        String [] arguments = new String [] {matchLevel, matchName, matchSurface,
                p1, p1Age, p1Fatigue, p1Hand, p1Height, p1Id, p1Rank, p1Point,
                p2, p2Age, p2Fatigue, p2Hand, p2Height, p2Id, p2Rank, p2Point};

        engine.put(ScriptEngine.ARGV, arguments);

        engine.eval(new FileReader(resolvePythonScriptPath(System.getProperty("user.dir") +
                "/pythonScript/prediction_API_for_spring/Prediction_API.py")), context);
        return writer.toString().trim();
    }

    private String resolvePythonScriptPath(String path){
        File file = new File(path);
        return file.getAbsolutePath();
    }

}
