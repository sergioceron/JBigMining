package clusterers;

import core.Dataset;
import core.PRObject;
import core.Structure;
import core.interfaces.IClusterer;
import core.interfaces.IDissimilarity;
import dissimilarities.Euclidean;
import exceptions.ArgumentOutOfRangeException;
import utils.DatasetCenter;
import utils.Nearest;
import utils.PRObjectUtils;

import java.util.Random;

/**
 * Created by sergio on 17/05/15.
 */
public class KMeans implements IClusterer {

    private int grupos;
    private IDissimilarity<PRObject> dissimilarity;

    public KMeans() {
        grupos = 1;
        dissimilarity = new Euclidean();
    }

    public KMeans( int grupos ) throws ArgumentOutOfRangeException {
        if( grupos <= 0 )
            throw new ArgumentOutOfRangeException( "La cantidad de grupos debe ser mayor que cero" );

        this.grupos = grupos;
        dissimilarity = new Euclidean();
    }

    public Structure findClusters( Dataset source ) {
        Structure part = new Structure();
        Dataset centers = new Dataset( source.getMetaObject() );
        // hallando los centros aleatorios
        Random rdm = new Random( System.currentTimeMillis() );
        for( int i = 0; i < grupos; i++ ) {
            int idx = rdm.nextInt( source.size() );
            try {
                centers.add( source.get( idx ).clone() );
            } catch( CloneNotSupportedException e ) {
                e.printStackTrace();
            }
            Structure.Item item = new Structure.Item();
            item.setDescriptor( centers.get( i ) );
            item.setDataset( new Dataset( source.getMetaObject() ) );
            part.add( item );
        }
        // asignanado los objetos a los centros más cercanos
        for( int i = 0; i < source.size(); i++ ) {
            int idx = Nearest.findIndexNearest( centers, source.get( i ), true, dissimilarity );
            part.get( idx ).getDataset().add( source.get( i ) );
        }
        boolean centersChanged = false;

        while( !centersChanged ) {
            // reiniciando to do....
            for( int i = 0; i < part.size(); i++ )
                part.get( i ).getDataset().clear();
            // asignanado los objetos a los centros más cercanos
            for( int i = 0; i < source.size(); i++ ) {
                int idx = Nearest.findIndexNearest( centers, source.get( i ), true, dissimilarity );
                part.get( idx ).getDataset().add( source.get( i ) );
            }
            // actualizando los centros
            Dataset oldCenters = new Dataset( source.getMetaObject() );
            for( int i = 0; i < part.size(); i++ ) {
                if( ( part.get( i ).getDataset() != null ) ) {
                    if( ( part.get( i ).getDataset().size() != 0 ) ) {
                        try {
                            oldCenters.add( ( (PRObject) part.get( i ).getDescriptor() ).clone() );
                        } catch( CloneNotSupportedException e ) {
                            e.printStackTrace();
                        }
                        part.get( i ).setDescriptor( DatasetCenter.getCenter( part.get( i ).getDataset() ) );
                    }
                }
            }

            // comparando los centros actuales y los nuevos
            int j = 0;
            while( ( !centersChanged ) && ( j < oldCenters.size() ) ) {
                try {
                    if( !PRObjectUtils.equalsAccordingRange( oldCenters.get( j ), (PRObject) part.get( j ).getDescriptor(), 0.05 ) )
                        centersChanged = true;
                } catch( ArgumentOutOfRangeException e ) {
                    e.printStackTrace();
                }
                j++;
            }
        }
        return part;
    }
}
