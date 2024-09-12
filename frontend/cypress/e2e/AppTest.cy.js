describe('App Navigation', () => {
    beforeEach(() => {
      cy.visit('/');
    });
  
    it('should navigate to the home page', () => {
      cy.get('nav').contains('Inicio').click();
      cy.url().should('include', '/');
    });
  
    it('should navigate to the idiomas page', () => {
      cy.get('nav').contains('Idiomas').click();
      cy.url().should('include', '/idiomas');
    });
  
    it('should navigate to the palabras page', () => {
      cy.get('nav').contains('Palabras').click();
      cy.url().should('include', '/palabras');
    });
  });