package tests;

import model.GenerateState;
import model.Model;
import model.SolveState;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ModelStatePatternTest {

    @Test
    public void ModelState_GenerateState_RegenerateMaze(){
        Model model = new Model();
        model.setState(new GenerateState(model));

        assertTrue(model.getModelState() instanceof GenerateState);

        model.regenerateMaze();

        assertTrue(model.getModelState() instanceof GenerateState);
    }

    @Test
    public void ModelState_GenerateState_ClearMaze(){
        Model model = new Model();
        model.setState(new GenerateState(model));

        assertTrue(model.getModelState() instanceof GenerateState);

        model.clearMaze();

        assertTrue(model.getModelState() instanceof GenerateState);
    }

    @Test
    public void ModelState_GenerateState_Step(){
        Model model = new Model();
        model.setState(new GenerateState(model));

        assertTrue(model.getModelState() instanceof GenerateState);

        model.step();

        assertTrue(model.getModelState() instanceof GenerateState);
    }

    @Test
    public void ModelState_GenerateState_PlayPause(){
        Model model = new Model();
        model.setState(new GenerateState(model));

        assertTrue(model.getModelState() instanceof GenerateState);

        model.playPause();

        assertTrue(model.getModelState() instanceof GenerateState);
    }

    @Test
    public void ModelState_GenerateState_Solve(){
        Model model = new Model();
        model.setState(new GenerateState(model));

        assertTrue(model.getModelState() instanceof GenerateState);

        model.solve();

        assertTrue(model.getModelState() instanceof SolveState);
    }

    @Test
    public void ModelState_GenerateState_Generate(){
        Model model = new Model();
        model.setState(new GenerateState(model));

        assertTrue(model.getModelState() instanceof GenerateState);

        model.solve();

        assertTrue(model.getModelState() instanceof GenerateState);
    }

    @Test
    public void ModelState_SolveState_RegenerateMaze(){
        Model model = new Model();
        model.setState(new SolveState(model));

        assertTrue(model.getModelState() instanceof SolveState);

        model.regenerateMaze();

        assertTrue(model.getModelState() instanceof GenerateState);
    }

    @Test
    public void ModelState_SolveState_ClearMaze(){
        Model model = new Model();
        model.setState(new SolveState(model));

        assertTrue(model.getModelState() instanceof SolveState);

        model.clearMaze();

        assertTrue(model.getModelState() instanceof SolveState);
    }

    @Test
    public void ModelState_SolveState_Step(){
        Model model = new Model();
        model.setState(new SolveState(model));

        assertTrue(model.getModelState() instanceof SolveState);

        model.step();

        assertTrue(model.getModelState() instanceof SolveState);
    }

    @Test
    public void ModelState_SolveState_PlayPause(){
        Model model = new Model();
        model.setState(new SolveState(model));

        assertTrue(model.getModelState() instanceof SolveState);

        model.playPause();

        assertTrue(model.getModelState() instanceof SolveState);
    }

    @Test
    public void ModelState_SolveState_Solve(){
        Model model = new Model();
        model.setState(new SolveState(model));

        assertTrue(model.getModelState() instanceof SolveState);

        model.solve();

        assertTrue(model.getModelState() instanceof SolveState);
    }

    @Test
    public void ModelState_SolveState_Generate(){
        Model model = new Model();
        model.setState(new SolveState(model));

        assertTrue(model.getModelState() instanceof SolveState);

        model.clearMaze();

        assertTrue(model.getModelState() instanceof GenerateState);
    }
}