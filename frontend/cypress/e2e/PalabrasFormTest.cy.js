describe('Palabras Form', () => {
    beforeEach(() => {
      cy.visit('/palabras-form');
    });
  
    it('should display the form', () => {
      cy.get('form.add-palabra-form').should('be.visible');
    });
  
    it('should submit the form', () => {
      cy.get('#palabra').type('Test Palabra');
      cy.get('#descripcion').type('Test Descripción');
      cy.get('#ejemploUso').type('Test Ejemplo de uso');
      cy.get('#nivelDificultad').type('5');
      cy.get('#frecuenciaUso').type('5');
      cy.get('#idioma').select(1); 
      cy.get('form.add-palabra-form').submit();
      cy.url().should('include', '/palabras');
    });
  });