import java.util.ArrayList;

public class Artifact extends Treasure
{
    private boolean inTomb;
    
    public Artifact(int number, int value)
    {
        super(number, value);
        this.inTomb = true;
    }
    
    public Artifact(Artifact a)
    {
        super(a.getNumber(), a.value);
        this.inTomb = a.inTomb;
    }

    public boolean isInTomb()
    {
        return this.inTomb;
    }

    @Override
    public String name()
    {
        String nameOfArtifact;

        switch (this.getNumber())
        {
            case 0:
                nameOfArtifact = "Meteoric Dagger";
                break;
            case 1:
                nameOfArtifact = "Ankh";
                break;
            case 2:
                nameOfArtifact = "Falcon Pectoral";
                break;
            case 3:
                nameOfArtifact = "Crook and Flail";
                break;
            case 4:
                nameOfArtifact = "Mask of Tutankhamun";
                break;
            default:
                nameOfArtifact = "Unknown";
                break;
        }

        return nameOfArtifact;
    }
    
    @Override
    public void share(ArrayList<Agent> receivers)
    {
        if (receivers.size() == 1 && !receivers.get(0).isStay() && this.inTomb)
        {
            receivers.get(0).getPossessionOfArtifacts().add(this);
            this.inTomb = false;
        }
    }
    
    @Override
    public String toString()
    {
        return String.format("%s <Artifact %d with value %d>", this.name(), this.getNumber(), this.value);
    }
}
