package tn.esprit.spring.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tn.esprit.spring.entities.Train;
import tn.esprit.spring.entities.Ville;
import tn.esprit.spring.entities.Voyage;
import tn.esprit.spring.entities.Voyageur;
import tn.esprit.spring.repository.TrainRepository;
import tn.esprit.spring.repository.VoyageRepository;
import tn.esprit.spring.repository.VoyageurRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.*;

class TrainServiceImplTest {

    @Mock
    private VoyageurRepository voyageurRepository;

    @Mock
    private TrainRepository trainRepository;

    @Mock
    private VoyageRepository voyageRepository;

    @InjectMocks
    private TrainServiceImpl trainService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void ajouterTrain_shouldSaveTrain() {
        Train train = new Train();

        trainService.ajouterTrain(train);

        verify(trainRepository, times(1)).save(train);
    }

    @Test
    void TrainPlacesLibres_shouldReturnCorrectCount() {
        Ville nomGareDepart = Ville.EZZAHRA;
        List<Voyage> voyages = new ArrayList<>();
        Train train1 = new Train();
        train1.setNbPlaceLibre(3);
        Train train2 = new Train();
        train2.setNbPlaceLibre(2);
        Voyage voyage1 = new Voyage();
        voyage1.setGareDepart(nomGareDepart);
        voyage1.setTrain(train1);
        Voyage voyage2 = new Voyage();
        voyage2.setGareDepart(nomGareDepart);
        voyage2.setTrain(train2);
        voyages.add(voyage1);
        voyages.add(voyage2);

        when(voyageRepository.findAll()).thenReturn(voyages);

        int result = trainService.TrainPlacesLibres(nomGareDepart);

        Assertions.assertEquals(2, result);
    }

    @Test
    void ListerTrainsIndirects_shouldReturnTrains() {
        Ville nomGareDepart = Ville.EZZAHRA;
        Ville nomGareArrivee = Ville.RADES;
        List<Voyage> voyages = new ArrayList<>();
        Train train1 = new Train();
        Train train2 = new Train();
        Voyage voyage1 = new Voyage();
        voyage1.setGareDepart(nomGareDepart);
        voyage1.setGareArrivee(nomGareArrivee);
        voyage1.setTrain(train1);
        Voyage voyage2 = new Voyage();
        voyage2.setGareDepart(nomGareArrivee);
        voyage2.setGareArrivee(nomGareArrivee);
        voyage2.setTrain(train2);
        voyages.add(voyage1);
        voyages.add(voyage2);

        when(voyageRepository.findAll()).thenReturn(voyages);

        List<Train> result = trainService.ListerTrainsIndirects(nomGareDepart, nomGareArrivee);

        Assertions.assertEquals(2, result.size());
        Assertions.assertEquals(train1, result.get(0));
        Assertions.assertEquals(train2, result.get(1));
    }

    @Test
    void affecterTainAVoyageur_shouldAffectTrainToVoyageur() {
        Long idVoyageur = 1L;
        Ville nomGareDepart = Ville.RADES;
        Ville nomGareArrivee = Ville.EZZAHRA;
        double heureDepart = 12.5;
        Voyageur voyageur = new Voyageur();
        Voyage voyage = new Voyage();
        voyage.setTrain(new Train());
        List<Voyage> voyages = new ArrayList<>();
        voyages.add(voyage);

        when(voyageurRepository.findById(idVoyageur)).thenReturn(java.util.Optional.of(voyageur));
        when(voyageRepository.RechercheVoyage(nomGareDepart, nomGareDepart, heureDepart)).thenReturn(voyages);

        trainService.affecterTainAVoyageur(idVoyageur, nomGareDepart, nomGareArrivee, heureDepart);

        verify(voyageRepository, times(1)).save(voyage);
    }

}
