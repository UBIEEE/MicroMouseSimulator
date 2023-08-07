package model.states;

import controller.ViewUpdatePacket;
import model.Model;
import model.generators.Generator;
import model.generators.GeneratorAlgorithms;
import model.solvers.Solver;
import model.solvers.SolverAlgorithms;

import java.lang.reflect.InvocationTargetException;

public class GenerateState extends ModelState {

    private Generator generatorAlgo;

    public Generator getGeneratorAlgo() {
        return generatorAlgo;
    }

    public void setGeneratorAlgo(Generator generatorAlgo) {
        this.generatorAlgo = generatorAlgo;
    }

    public GenerateState(Model model){
        super(model);
        this.selectedGenerationAlgo = GeneratorAlgorithms.Prim.getClazz();
        this.selectedSolvingAlgo = SolverAlgorithms.BFS.getClazz();
        this.generate();
    }

    public GenerateState(Model model, Class<? extends Generator> selectedGenerationAlgo, Class<? extends Solver> selectedSolvingAlgo){
        super(model);
        this.selectedGenerationAlgo = selectedGenerationAlgo;
        this.selectedSolvingAlgo = selectedSolvingAlgo;
        this.generate();
    }

    public ViewUpdatePacket updateView(){
        return this.getGeneratorAlgo().makeViewUpdatePacket();
    }

    public void clearMaze(){
        return;
    }

    public void step(){
        this.getGeneratorAlgo().iterate();
    }

    public void finish(){
        //TODO
        this.getGeneratorAlgo().finish();
        return;
    }

    public void generate(){
        try {
            this.setGeneratorAlgo(this.selectedGenerationAlgo.getDeclaredConstructor().newInstance());
        } catch (InstantiationException | InvocationTargetException | NoSuchMethodException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public void solve(){
        if (this.getGeneratorAlgo().getDoneGenerating()){
            this.model.setState(new SolveState(this.model, this.selectedGenerationAlgo, this.selectedSolvingAlgo));
        }
        return;
    }
}
