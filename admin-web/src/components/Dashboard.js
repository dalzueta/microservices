const Dashboard = () => {
    return (
      <div className="dashboard">
        <h1>Bienvenido al Dashboard</h1>
        <div className="buttons">
          <button onClick={() => alert("Funcionalidad 1")}>Funcionalidad 1</button>
          <button onClick={() => alert("Funcionalidad 2")}>Funcionalidad 2</button>
          <button onClick={() => alert("Funcionalidad 3")}>Funcionalidad 3</button>
        </div>
      </div>
    );
  };
  
  export default Dashboard;
  