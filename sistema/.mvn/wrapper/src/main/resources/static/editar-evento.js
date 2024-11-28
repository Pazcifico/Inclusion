// Função para carregar os dados do evento selecionado
function loadEventData() {
    const eventId = document.getElementById('eventSelect').value;

    if (eventId) {
        // Aqui você pode buscar os dados do evento no servidor ou usar dados simulados
        const eventData = getEventDataById(eventId);

        // Preencher os campos com os dados do evento
        document.getElementById('eventName').value = eventData.name;
        document.getElementById('eventDateTime').value = eventData.dateTime;
        document.getElementById('eventLocation').value = eventData.location;
        document.getElementById('eventDescription').value = eventData.description;
        document.getElementById('ticketType').value = eventData.ticketType;
        toggleTicketFields();  // Mostrar ou ocultar campos de ingresso baseado no tipo
        if (eventData.ticketType === 'paid') {
            document.getElementById('ticketValue').value = eventData.ticketValue;
            document.getElementById('ticketQuantity').value = eventData.ticketQuantity;
        }
        document.getElementById('eventStart').value = eventData.startTime;
        document.getElementById('eventEnd').value = eventData.endTime;
        document.getElementById('eventType').value = eventData.type;
        document.getElementById('eventTags').value = eventData.tags;
    }
}

// Função para simular a obtenção de dados de um evento (você pode substituí-la por uma chamada a uma API)
function getEventDataById(eventId) {
    // Dados de exemplo (simulados)
    const events = {
        '1': {
            name: 'Evento Exemplo 1',
            dateTime: '2024-12-01T14:00',
            location: 'Rua Exemplo, 123',
            description: 'Descrição do evento 1',
            ticketType: 'paid',
            ticketValue: 20.00,
            ticketQuantity: 100,
            startTime: '2024-12-01T14:00',
            endTime: '2024-12-01T18:00',
            type: 'Cultural',
            tags: 'Música, Arte'
        },
        '2': {
            name: 'Evento Exemplo 2',
            dateTime: '2024-12-10T09:00',
            location: 'Av. Teste, 456',
            description: 'Descrição do evento 2',
            ticketType: 'free',
            ticketValue: null,
            ticketQuantity: null,
            startTime: '2024-12-10T09:00',
            endTime: '2024-12-10T12:00',
            type: 'Esportivo',
            tags: 'Esportes, Aventura'
        }
    };

    return events[eventId] || {};
}

// Função para mostrar ou ocultar os campos de ingresso pagos
function toggleTicketFields() {
    const ticketType = document.getElementById('ticketType').value;
    const paidTicketFields = document.getElementById('paidTicketFields');

    if (ticketType === 'paid') {
        paidTicketFields.style.display = 'block';
    } else {
        paidTicketFields.style.display = 'none';
    }
}

// Função para validar e enviar os dados ao servidor
document.getElementById('editEventForm').addEventListener('submit', function(event) {
    event.preventDefault(); // Impede o envio do formulário
    
    const eventName = document.getElementById('eventName').value;
    const eventDateTime = document.getElementById('eventDateTime').value;
    const eventLocation = document.getElementById('eventLocation').value;
    const eventDescription = document.getElementById('eventDescription').value;
    const eventStart = document.getElementById('eventStart').value;
    const eventEnd = document.getElementById('eventEnd').value;
    
    // Verificar se todos os campos obrigatórios estão preenchidos
    if (!eventName || !eventDateTime || !eventLocation || !eventDescription || !eventStart || !eventEnd) {
        alert('Por favor, preencha todos os campos obrigatórios.');
        return;
    }

    // Se o tipo de ingresso for "pago", verificar se o valor e a quantidade foram preenchidos
    const ticketType = document.getElementById('ticketType').value;
    if (ticketType === 'paid') {
        const ticketValue = document.getElementById('ticketValue').value;
        const ticketQuantity = document.getElementById('ticketQuantity').value;
        
        if (!ticketValue || !ticketQuantity) {
            alert('Por favor, preencha o valor e a quantidade de ingressos.');
            return;
        }
    }

    // Se todos os campos estão preenchidos, podemos enviar os dados (ou fazer algo com eles)
    alert('Evento editado com sucesso!');
    // Aqui você pode adicionar o código para enviar os dados ao servidor ou realizar outras ações.
});

