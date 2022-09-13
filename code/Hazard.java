public class Hazard extends Card
{
    public Hazard(int number)
    {
        super(number);
    }
    
    public Hazard(Hazard h)
    {
        super(h.getNumber());
    }
    
    @Override
    public String name()
    {
        String nameOfHazard;

        switch (this.getNumber())
        {
            case 0:
                nameOfHazard = "Spikes";
                break;
            case 1:
                nameOfHazard = "Spiders";
                break;
            case 2:
                nameOfHazard = "Mummy";
                break;
            case 3:
                nameOfHazard = "Curse";
                break;
            case 4:
                nameOfHazard = "Collapse";
                break;
            default:
                nameOfHazard = "Unknown";
                break;
        }

        return nameOfHazard;
    }
    
    @Override
    public String toString()
    {
        return String.format("%s <Hazard %d>", this.name(), this.getNumber());
    }
}
