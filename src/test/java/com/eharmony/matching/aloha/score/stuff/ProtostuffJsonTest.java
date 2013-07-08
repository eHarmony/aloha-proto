package com.eharmony.matching.aloha.score.stuff;

import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.*;
import org.junit.runners.BlockJUnit4ClassRunner;

import com.dyuproject.protostuff.JsonIOUtil;

import com.eharmony.matching.aloha.score.SchemaScores;
import com.eharmony.matching.aloha.score.Scores.Score;
import com.eharmony.matching.aloha.score.Scores.Score.ScoreError;
import com.eharmony.matching.aloha.score.Scores.Score.ModelId;

import java.io.ByteArrayOutputStream;
import java.util.Arrays;
import java.util.List;

@RunWith(BlockJUnit4ClassRunner.class)
public class ProtostuffJsonTest {
    /**
     * Test basic serialization:
     * <ol>
     *     <li>Create a basic error score protocol buffer instance.</li>
     *     <li>Create a ByteArrayOutputStream</li>
     *     <li>Use Protostuff to serialize the to json and output to the output stream</li>
     *     <li>Compare the JSON output to the possible permutations of the compressed JSON.</li>
     * </ol>
     * @throws Exception
     */
    @Test
    public void test() throws Exception {
        final Score score = Score.newBuilder()
            .setError(ScoreError.newBuilder()
                    .setModel(ModelId.newBuilder().setId(1).setName(""))
                    .addMessages("message_here"))
            .build();

        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        JsonIOUtil.writeTo(baos, score, SchemaScores.Score.WRITE, false);

        final List<String> possibles = Arrays.asList(new String[]{
                "{\"error\":{\"model\":{\"id\":1,\"name\":\"\"},\"messages\":[\"message_here\"]}}",
                "{\"error\":{\"messages\":[\"message_here\"], \"model\":{\"id\":1,\"name\":\"\"}}}",
                "{\"error\":{\"messages\":[\"message_here\"], \"model\":{\"name\":\"\",\"id\":1}}}"
        });

        final String json = baos.toString();

        boolean in = false;

        for (String possible: possibles)
            if (possible.equals(json))
                in = true;

        assertTrue("generated json should have been one of " + possibles + ", found " + json, in);
    }
}
