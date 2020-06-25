package com.tecnoupsa.ecopass_betav10.Grafos;

public class Grafo {
    public Lista LVertices;

    public Grafo() {
        LVertices = new Lista();
    }

    public void crearVertice(String nomV, Double lat, Double log){
        LVertices.insertarUlt(new Vertice(nomV,lat,log));
    }

    public Vertice buscarVertice(String nomV)
    {
        Vertice v;
        int i=0;
        while (i<LVertices.dim())
        {
            v =(Vertice)LVertices.getElem(i);
            if (v.getNombre().equals(nomV))
                return v;
            i++;
        }
        return null;
    }

    public void insertarArco(String X,String Y, int co)
    {
        Vertice vo = buscarVertice(X);
        Vertice vd = buscarVertice(Y);
        vo.insertarArco(new Arco(vd, co));
    }
/*
    public void imprimir(JTextArea jta){
        int i = 0,j; Vertice v; Arco a;
        while (i < LVertices.dim())
        {
            v = (Vertice)LVertices.getElem(i);
            j=0;
            while (j<v.LArcos.dim())
            {
                jta.append(v.getNombre());
                jta.append("-->");
                a = (Arco)v.LArcos.getElem(j); //Muestra el arco donde apunto
                jta.append(a.getNombreVertD() + "  " + a.getCosto());
                jta.append("\n");
                j++;
            }
            i++;
        }
    }
*/
    //DESDE AQUI METODOS DE ENSEÑANZA
    public float peso() {
        int i = 0;
        Vertice v; float s = 0;
        while (i < LVertices.dim())
        {
            v = (Vertice)LVertices.getElem(i);
            int j=0; Arco a;
            while(j < v.LArcos.dim())
            {
                a=(Arco)v.LArcos.getElem(j);
                s = s + a.getCosto();
                j++;
            }
            i++;
        }
        return s;
    }
/*
    public void arcosSalientes(){
        int i = 0;
        Vertice v;
        while (i < LVertices.dim()) {
            v = (Vertice)LVertices.getElem(i);
            JOptionPane.showMessageDialog(null,v.getNombre() + "  " + v.LArcos.dim());
            i++;
        }
    }
*/
    public void eliminarArco(String X, String Y)
    {
        Vertice v;
        v = buscarVertice(X);
        int i = 0;
        Arco a;
        while (i < v.LArcos.dim())
        {
            a = (Arco)v.LArcos.getElem(i);
            if (a.getNombreVertD().equals(Y))
                v.LArcos.eliminar(i);
            i++;
        }
    }

    public void eliminarArcosSalientes(String X)
    {
        Vertice v;
        v = buscarVertice(X);
        int j = 0; int n=v.LArcos.dim();
        while (j < n ) {
            v.LArcos.eliminarUlt();
            j++;
        }
    }

    public void eliminarArcosEntrantes(String X)
    {
        Vertice v; Arco a;
        int i = 0,j;
        while (i < LVertices.dim() ) {
            v = (Vertice)LVertices.getElem(i);
            j=0; int n=v.LArcos.dim();
            while( j < n ){
                a=(Arco)v.LArcos.getElem(j);
                if(a.getNombreVertD().equals(X))
                    v.LArcos.eliminar(j);
                j++;
            }
            i++;
        }
    }

    public void eliminarVertice(String X)
    {
        Vertice vx=buscarVertice(X);
        if(vx==null)
            return ;
        eliminarArcosEntrantes(X);
        eliminarArcosSalientes(X);
        Vertice v; int i=0;
        while(i<LVertices.dim()){
            v = (Vertice)LVertices.getElem(i);
            if(v.getNombre().equals(X)) {
                LVertices.eliminar(i);
                return ;
            }
            i++;
        }
    }
    public void desmarcarTodos()
    {
        for(int i=0;i<this.LVertices.dim();i++){
            Vertice v=(Vertice)this.LVertices.getElem(i);
            v.marcado=false;
        }
    }
    public int cantidadCaminosDFS(String o,String d)//,JTextArea jta)
    {
        if(buscarVertice(o)!=null &&buscarVertice(d)!=null)
        {
            desmarcarTodos();
            Vertice v=buscarVertice(o);
            return cantidadCaminosDFS(v,d);
        }
        return -1;
    }
    private int cantidadCaminosDFS(Vertice v,String d)//,JTextArea jta)
    {
        int i=0,c=0;
        v.marcado=true;
        while(i<v.LArcos.dim())
        {
            //jta.append(v.getNombre());
            Arco a=(Arco) v.LArcos.getElem(i);
            Vertice vo=buscarVertice(a.getNombreVertD());
            if(!vo.marcado)
            {
                if(vo.getNombre().equals(d))
                {
                    //jta.append(vo.getNombre());
                    //jta.append("\n");
                    c=c+1;
                }
                else
                {
                    c=c+cantidadCaminosDFS(vo,d);
                    vo.marcado=false;
                }
            }
            i++;
        }
        return c;
    }
    public String caminoCortoDFS(String o,String d)
    {
        String g=new String();
        String s="";
        if(buscarVertice(o)!=null && buscarVertice(d)!= null)
        {

            desmarcarTodos();
            Vertice v=buscarVertice(o);
            g= caminoCortoDFS(v,d,s);
            return g;
        }
        return g;
    }
    private String caminoCortoDFS(Vertice v,String d,String x)
    {

        String[] s=new String[99];
        int i=0,j=1;
        v.marcado=true;
        while(i<v.LArcos.dim())
        {
            x=x+v.getNombre();
            //jta.append(v.getNombre());
            Arco a=(Arco) v.LArcos.getElem(i);
            Vertice vo=buscarVertice(a.getNombreVertD());
            if(!vo.marcado)
            {
                if(vo.getNombre().equals(d))
                {
                    x=x+vo.getNombre();
                    //jta.append(vo.getNombre());
                    //jta.append("\n");



                }
                else
                {
                    x=caminoCortoDFS(vo,d,x);
                    vo.marcado=false;
                }
            }
            i++;
            j++;
        }
        return x;
    }
    public String[] caminos(String m,String o,String d)
    {
        String[] caminos=m.split(d);
        int x=cantidadCaminosDFS(o,d);
        for(int i=0;i<x;i++)
        {
            caminos[i]=caminos[i]+d;
        }
        return caminos;
    }

    public String comparar(String[] c,String o,String d)
    {
        int co1=0,co2=0,costo=1000,m=cantidadCaminosDFS(o,d);
        String aux1 =null,aux2=null,x = null;
        if(m>1)
        {
            for (int i=0;i<m-1;i++)
            {
                aux1=c[i];aux2=c[i+1];
                co1=costoTotal(aux1,o,d);
                co2=costoTotal(aux2,o,d);
                if(costo>co1&&costo>co2)
                {
                    if(co2>co1)
                    {
                        x=aux1;
                        costo=co1;
                    }
                    else
                    {
                        x=aux2;
                        costo=co2;
                    }
                }
            }
        }
        if(m==1)
            x=c[0];
        return x;
    }
    public int devolverCosto(String[] c,String o,String d)
    {
        int co1=0,co2=0,costo=1000,m=cantidadCaminosDFS(o,d);
        String aux1 =null,aux2=null,x = null;
        if(m>1)
        {
            for (int i=0;i<m-1;i++)
            {
                aux1=c[i];aux2=c[i+1];
                co1=costoTotal(aux1,o,d);
                co2=costoTotal(aux2,o,d);
                if(costo>co1&&costo>co2)
                {
                    if(co2>co1)
                    {
                        x=aux1;
                        costo=co1;
                    }
                    else
                    {
                        x=aux2;
                        costo=co2;
                    }
                }
            }
        }
        if(m==1)
            x=c[0];
        return costo;
    }
    public int costoTotal(String x,String origen,String destino)
    {
        int c=0;
        char[] aux=x.toCharArray();
        String[] s=new String[aux.length];
        for(int i=0;i<aux.length;i++)
        {
            s[i]=String.valueOf(aux[i]);
        }
        if(s[0].equals(origen))
        {

            for(int j=0;j<s.length-1;j++)
            {
                c=c+Costo(s[j],s[j+1]);
            }
        }
        else
        {
            c=c+Costo(origen,s[0]);
            for(int j=0;j<s.length-1;j++)
            {
                c=c+Costo(s[j],s[j+1]);
            }
        }
        return c;
    }
    public int Costo(String x,String y)
    {
        int c=0;
        Vertice v=buscarVertice(x);
        for(int i=0;i<v.LArcos.dim();i++)
        {
            Arco a=(Arco)v.LArcos.getElem(i);
            if(a.getNombreVertD().equals(y))
            {
                c=c+a.getCosto();
            }
        }
        return c;
    }

}
