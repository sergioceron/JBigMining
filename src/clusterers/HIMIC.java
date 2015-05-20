package clusterers;

import core.Dataset;
import core.Structure;
import dissimilarities.HIMICDissimilarity;
import exceptions.ArgumentOutOfRangeException;
import utils.NormalizeNumericalAtt;

/**
 * Created by sergio on 17/05/15.
 */
public class HIMIC implements core.interfaces.IClusterer {

    private int Cant_Grupos;
    private HIMICDissimilarity dissimilarity;

    public HIMIC( int cant ) throws ArgumentOutOfRangeException {
        if( cant <= 0 )
            throw new ArgumentOutOfRangeException( "La cantidad de grupos debe ser mayor que cero" );

        Cant_Grupos = cant;
        dissimilarity = new HIMICDissimilarity();
    }


    public Structure findClusters( Dataset source ) {
        Structure part = new Structure();
        Dataset normalized = null;
        try {
            normalized = NormalizeNumericalAtt.normalize( source );
        } catch( CloneNotSupportedException e ) {
            e.printStackTrace();
        }
        // cada objeto es un grupo inicialmente
        if( normalized != null ) {
            for( int i = 0; i < normalized.size(); i++ ) {
                Structure.Item item = new Structure.Item();
                item.setDescriptor( source.get( i ) );
                item.setDataset( new Dataset( source.getMetaObject() ) );
                item.getDataset().add( source.get( i ) );
                part.add( item );
            }


            while( part.size() > Cant_Grupos ) {
                //escoger los dos grupos m'as cercanos
                double[][] diss = new double[part.size()][part.size()];
                double min_value = Double.MAX_VALUE;
                int idx = -1;
                int idx2 = -1;
                for( int i = 0; i < part.size(); i++ )
                    for( int j = 0; j < part.size(); j++ ) {
                        diss[i][j] = dissimilarity.compare( part.get( i ).getDataset(), part.get( j ).getDataset() );
                        if( ( min_value > diss[i][j] ) && ( i != j ) ) {
                            min_value = diss[i][j];
                            idx = i;
                            idx2 = j;
                        }
                    }
                //unirlos
                part.get( idx ).getDataset().addAll( part.get( idx2 ).getDataset() );
                part.remove( idx2 );
            }
        }

        return part;
    }
}
