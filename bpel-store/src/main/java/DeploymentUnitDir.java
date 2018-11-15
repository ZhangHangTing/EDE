import java.io.File;

public class DeploymentUnitDir {
    private String _name;
    private File _duDirectory;
    private File _descriptorFile;

    public String get_name() {
        return _name;
    }

    public void set_name(String _name) {
        this._name = _name;
    }

    public File get_duDirectory() {
        return _duDirectory;
    }

    public void set_duDirectory(File _duDirectory) {
        this._duDirectory = _duDirectory;
    }

    public File get_descriptorFile() {
        return _descriptorFile;

    }

    public void set_descriptorFile(File _descriptorFile) {
        this._descriptorFile = _descriptorFile;
    }
    private void compile(File bpelFile){


    }
}
