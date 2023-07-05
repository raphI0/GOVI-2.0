import * as moment from 'moment-timezone';

// La date sera au format français pour tout le reste du code. Permet d'unifier avec le back et éviter de perdre des jours/heures dans les échanges d'API
moment.tz.setDefault('UTC'); // Europe/Paris pour UTC+2 fr, mais dur à faire correspondre dans le back et donc amène des décalages lors de l'appel d'API

export const environment = {
  production: true,
  titre: 'GOVI-Front',

  url: {
    // URL (exposées) à atteindre pour les appels d'API du back-end Java
    api: {
      base: 'http://localhost:8080',
      envoiFichierGovi: '/generationGOVI/file',
      generationGovi: '/generationGOVI',
    },

    // URL choisis pour les variations de notre single page app
    route: {
      accueil: '',
      generateurGovi: 'generateurGOVI',
      affichageCreation: 'afficheurGOVI',
      affichageImport: 'afficheurImportGOVI',
    },
  },
};
