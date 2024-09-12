describe('Home View', () => {
    beforeEach(() => {
      cy.visit('/');
    });
  
    it('should display the welcome message', () => {
      cy.contains('¡Bienvenido a tu diccionario!').should('be.visible');
    });
  
    it('should navigate to the idiomas list', () => {
      cy.get('button').contains('Idiomas').click();
      cy.url().should('include', '/idiomas');
    });
  
    it('should navigate to the palabras list', () => {
      cy.get('button').contains('Palabras').click();
      cy.url().should('include', '/palabras');
    });
  });