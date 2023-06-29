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
