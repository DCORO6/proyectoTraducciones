describe('Idiomas List', () => {
    beforeEach(() => {
      cy.visit('/idiomas');
    });
  
    it('should display the list of idiomas', () => {
      cy.get('.cards-container').should('be.visible');
    });
  
    it('should navigate to the form to create a new idioma', () => {
      cy.get('.btn-create').click();
      cy.url().should('include', '/idiomas-form');
    });

  
  
    it('should update idioma in edit mode', () => {
      cy.visit('/idiomas-form?id=1'); 
      cy.get('#nombre').clear().type('Updated Idioma');
      cy.get('#codigoIso').clear().type('UPD');
      cy.get('#alfabeto').select('Tibetano');
      cy.get('#region').clear().type('Updated Región');
      cy.get('form.add-idioma-form').submit();
      cy.url().should('include', '/idiomas');
      cy.contains('Idioma Updated Idioma actualizado exitosamente').should('be.visible');
    });
  });