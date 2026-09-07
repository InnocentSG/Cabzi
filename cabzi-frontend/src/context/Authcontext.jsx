import {

  createContext,

  useEffect,

  useState

} from "react";

export const AuthContext =
  createContext();

function AuthProvider({

  children

}) {

  // =================================
  // USER STATE
  // =================================

  const [user, setUser] =
    useState(null);

  // =================================
  // LOADING STATE
  // =================================

  const [loading, setLoading] =
    useState(true);

  // =================================
  // RESTORE USER
  // =================================

  useEffect(() => {

    try {

      const storedUser =
        localStorage.getItem("user");

      if (storedUser) {

        setUser(
          JSON.parse(storedUser)
        );
      }

    } catch (error) {

      console.error(
        "Auth restore error:",
        error
      );
    }

    setLoading(false);

  }, []);

  // =================================
  // LOGIN
  // =================================

  const login = (

    userData,

    token

  ) => {

    localStorage.setItem(
      "token",
      token
    );

    localStorage.setItem(
      "user",
      JSON.stringify(userData)
    );

    setUser(userData);
  };

  // =================================
  // LOGOUT
  // =================================

  const logout = () => {

    localStorage.removeItem(
      "token"
    );

    localStorage.removeItem(
      "user"
    );

    setUser(null);

    window.location.href =
      "/login";
  };

  return (

    <AuthContext.Provider
      value={{

        user,

        loading,

        login,

        logout

      }}
    >

      {children}

    </AuthContext.Provider>
  );
}

export default AuthProvider;