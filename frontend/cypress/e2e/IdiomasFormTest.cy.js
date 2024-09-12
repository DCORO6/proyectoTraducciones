describe('Idiomas Form', () => {
    beforeEach(() => {
      cy.visit('/idiomas-form');
    });
  
    it('should display the form', () => {
      cy.get('form.add-idioma-form').should('be.visible');
    });
  
    it('should submit the form', () => {
      cy.get('#nombre').type('Test Idioma');
      cy.get('#codigoIso').type('TES');
      cy.get('#alfabeto').select('Latino');
      cy.get('#region').type('Test Región');
      cy.get('form.add-idioma-form').submit();
      cy.url().should('include', '/idiomas');
    });

    
  });