public class verificaPagamentiPeriodica implements Runnable{
    private Server s;
    public verificaPagamentiPeriodica(Server s) {
        this.s=s;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(60000);
                s.listaDebitori.clear();
                for(Corso c: s.getListaCorsi()){
                    if(c.getListaIscritti()!=null)
                        for(Iscritto i: c.getListaIscritti()){
                            if(!i.isPagamento()){
                                s.listaDebitori.add(i);
                            }
                        }
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
